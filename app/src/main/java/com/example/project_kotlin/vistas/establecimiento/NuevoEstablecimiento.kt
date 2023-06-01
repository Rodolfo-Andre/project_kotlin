package com.example.project_kotlin.vistas.establecimiento

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.R
import com.example.project_kotlin.dao.EstablecimientoDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.Establecimiento
import com.example.project_kotlin.utils.appConfig
import com.example.project_kotlin.vistas.mesas.DatosMesas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevoEstablecimiento:AppCompatActivity() {

        private  lateinit var edtNombre:EditText
        private lateinit var edtDireccion:EditText
        private lateinit var edtRuc:EditText
        private lateinit var edtTelefono:EditText
        private lateinit var establecimientoDao:EstablecimientoDao
        private lateinit var btnaAgregarEstablecimiento:Button
        private lateinit var btnVolverListadoEstablecimiento:Button

        private  val REGEX_NOMBRE = "^(?=.{3,100}$)[A-ZÑÁÉÍÓÚ][A-ZÑÁÉÍÓÚa-zñáéíóú]+(?: [A-ZÑÁÉÍÓÚa-zñáéíóú]+)*$"
        private  val REGEX_DIRECCION = "^(?=.{3,100}$)[A-ZÑÁÉÍÓÚ][A-Za-zñáéíóú0-9.\\-]+(?: [A-Za-zñáéíóú0-9.\\-]+)*$"
        private  val REGEX_TELEFONO = "^9[0-9]{8}$"
        private  val REGEX_RUC = "^[0-9]{11}$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agregar_establecimiento)

        edtNombre = findViewById(R.id.edtNombre)
        edtDireccion = findViewById(R.id.edtDireccion)
        edtRuc = findViewById(R.id.edtRuc)
        edtTelefono = findViewById(R.id.edtTelefono)
        btnVolverListadoEstablecimiento = findViewById(R.id.btnCancelarEstablecimiento)
        establecimientoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).establecimientoDao()
        btnaAgregarEstablecimiento = findViewById(R.id.btnNuevoEstablecimiento)

       btnVolverListadoEstablecimiento.setOnClickListener { volver() }
        btnaAgregarEstablecimiento.setOnClickListener { agregarEstablecimiento() }
    }

    fun agregarEstablecimiento(){
        lifecycleScope.launch(Dispatchers.IO) {
            if(validarCampos()){
                val nombre=edtNombre.text.toString()
                val direccion=edtDireccion.text.toString()
                val ruc=edtRuc.text.toString()
                val telefono=edtTelefono.text.toString()

                val bean= Establecimiento(nombreEstablecimiento = nombre,
                                          direccionEstablecimiento = direccion,
                                          rucEstablecimiento = ruc,
                                          telefonoEstablecimiento = telefono)
                establecimientoDao.guardar(bean)
                mostrarToast("Establecimiento agregado correctamente")
                volver()

            }

        }

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

    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(appConfig.CONTEXT, mensaje, Toast.LENGTH_SHORT).show()
        }
    }
    fun volver() {
        val intent = Intent(this, DatosEstablecimiento::class.java)
        startActivity(intent)
    }





}