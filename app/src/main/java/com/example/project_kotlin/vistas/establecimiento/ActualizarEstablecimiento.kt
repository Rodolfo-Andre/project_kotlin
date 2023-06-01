package com.example.project_kotlin.vistas.establecimiento

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.R
import com.example.project_kotlin.dao.CajaDao
import com.example.project_kotlin.dao.EstablecimientoDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.Establecimiento
import com.example.project_kotlin.entidades.Mesa
import com.example.project_kotlin.utils.appConfig
import com.example.project_kotlin.vistas.mesas.DatosMesas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActualizarEstablecimiento:AppCompatActivity() {
    private lateinit var edtCod:EditText
    private lateinit var edtNombre:EditText
    private lateinit var edtDireccion:EditText
    private lateinit var edtRuc:EditText
    private lateinit var edtTelefono:EditText
    private lateinit var btnActualizar:Button
    private lateinit var btnEliminar:Button
    private lateinit var btnVolver:Button
    private lateinit var cajaDao:CajaDao
    private lateinit var establecimientoDao:EstablecimientoDao

    private  val REGEX_NOMBRE = "^(?=.{3,100}$)[A-ZÑÁÉÍÓÚ][A-ZÑÁÉÍÓÚa-zñáéíóú]+(?: [A-ZÑÁÉÍÓÚa-zñáéíóú]+)*$"
    private  val REGEX_DIRECCION = "^(?=.{3,100}$)[A-ZÑÁÉÍÓÚ][A-Za-zñáéíóú0-9.\\-]+(?: [A-Za-zñáéíóú0-9.\\-]+)*$"
    private  val REGEX_TELEFONO = "^9[0-9]{8}$"
    private  val REGEX_RUC = "^[0-9]{11}$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modificar_establecimiento)
        establecimientoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).establecimientoDao()
        cajaDao=ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).cajaDao()

        edtCod=findViewById(R.id.edtCodigoA)
        edtNombre = findViewById(R.id.edtNombreA)
        edtDireccion = findViewById(R.id.edtDireccionA)
        edtRuc = findViewById(R.id.edtRucA)
        edtTelefono = findViewById(R.id.edtTelefonoA)

        btnActualizar = findViewById(R.id.btnEditarEstablecimiento)
        btnEliminar = findViewById(R.id.btnEliminarEstablecimiento)
        btnVolver = findViewById(R.id.btnVolverListadoEstablecimiento)

        btnVolver.setOnClickListener { Volver() }
        btnEliminar.setOnClickListener { Eliminar() }
        btnActualizar.setOnClickListener { Editar() }

        //Cargar dato
        val establecimiento = intent.getSerializableExtra("establecimiento") as Establecimiento
        edtCod.setText(establecimiento.id.toString())
        edtNombre.setText(establecimiento.nombreEstablecimiento)
        edtDireccion.setText(establecimiento.direccionEstablecimiento)
        edtRuc.setText(establecimiento.rucEstablecimiento)
        edtTelefono.setText(establecimiento.telefonoEstablecimiento)

    }


    fun Editar() {
        val numEstablecimiento = edtCod.text.toString().toLong()
        lifecycleScope.launch(Dispatchers.IO) {
                if (validarCampos()) {
                    val nombre = edtNombre.text.toString()
                    val direccion = edtDireccion.text.toString()
                    val telefoono = edtTelefono.text.toString()
                    val ruc = edtRuc.text.toString();
                    val establecimiento = Establecimiento(id=numEstablecimiento,
                        nombreEstablecimiento = nombre,
                        direccionEstablecimiento = direccion,
                        telefonoEstablecimiento = telefoono,
                        rucEstablecimiento = ruc
                    )
                    establecimientoDao.actualizar(establecimiento)
                    mostrarToast("Establecimiento actualizado correctamente")
                    Volver()
                }


        }
        }


    fun Eliminar() {
        val numEstablecimiento = edtCod.text.toString().toInt()
        val mensaje: AlertDialog.Builder = AlertDialog.Builder(this)
        mensaje.setTitle("Sistema comandas")
        mensaje.setMessage("¿Seguro de eliminar?")
        mensaje.setCancelable(false)
        mensaje.setPositiveButton("Aceptar") { _, _ ->
            lifecycleScope.launch(Dispatchers.IO) {
                    //Validar caja
                    val validarCaja=cajaDao.obtenerCajaPorEstablecimiento(numEstablecimiento)
                    if(validarCaja.isEmpty()){
                        val eliminar = establecimientoDao.obtenerPorId(numEstablecimiento.toLong())
                        establecimientoDao.eliminar(eliminar)
                        mostrarToast("Establecimiento eliminado correctamente")
                        Volver()

                    }else{
                        mostrarToast("No puedes eliminar Establecimiento que tienen información en Caja")
                    }

                }
            }

        mensaje.setNegativeButton("Cancelar") { _, _ -> }
        mensaje.setIcon(android.R.drawable.ic_delete)
        mensaje.show()
    }

    fun validarCampos():Boolean{

        val nombre=edtNombre.text
        val direccion=edtDireccion.text
        val telefono=edtTelefono.text
        val ruc=edtRuc.text

        if (!REGEX_NOMBRE.toRegex().matches(nombre)) {
            // El campo nombre no cumple con el formato esperado

            mostrarToast("El campo nombre no cumple con el formato requerido. Debe comenzar con mayúscula y contener solo letras y espacios")
            return false
        }
        if(!REGEX_DIRECCION.toRegex().matches(direccion)) {
            mostrarToast("La dirección ingresada no cumple con el formato requerido. Debe comenzar con mayúscula. Ejemplo: Av. Principal 123-A.")
            return false
        }

        if(!REGEX_RUC.toRegex().matches(ruc)){
            mostrarToast("El RUC es incorrecto. Debe tener 11 dígitos numéricos")
            return false
        }
        if(!REGEX_TELEFONO.toRegex().matches(telefono)){
            mostrarToast("Introduce un número válido. Solo acepta 9 dígitos y comienza con 9")
            return false
        }

        return true

    }

    fun Volver() {
        val intent = Intent(this, DatosEstablecimiento::class.java)
        startActivity(intent)
    }
    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(appConfig.CONTEXT, mensaje, Toast.LENGTH_SHORT).show()
        }
    }


    }

