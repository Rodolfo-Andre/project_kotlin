package com.example.project_kotlin.vistas.metodo_pago

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.R
import com.example.project_kotlin.dao.DetalleComandaDao
import com.example.project_kotlin.dao.MetodoPagoDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.MetodoPago
import com.example.project_kotlin.utils.appConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActualizarMetodoPago : AppCompatActivity() {

    private lateinit var edtNomMetodoPago: EditText
    private lateinit var btnEditarPago: Button
    private lateinit var btnEliminarPago: Button
    private lateinit var btnVolverListadoPago: Button
    private lateinit var metodoPagoDao: MetodoPagoDao

    private lateinit var metodoPagoBean : MetodoPago

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modificar_pago)
        metodoPagoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).metodoPagoDao()

        edtNomMetodoPago = findViewById(R.id.edtNomMetodoPago)
        btnEditarPago = findViewById(R.id.btnEditarPago)
        btnEliminarPago = findViewById(R.id.btnEliminarPago)
        btnVolverListadoPago = findViewById(R.id.btnVolverListadoPago)

        btnVolverListadoPago.setOnClickListener { volver() }
        btnEliminarPago.setOnClickListener { eliminar() }
        btnEditarPago.setOnClickListener { editar() }

        //cargar datos
        metodoPagoBean = intent.getSerializableExtra("metodo_pago") as MetodoPago
        edtNomMetodoPago.setText(metodoPagoBean.nombreMetodoPago)

    }

    fun volver() {
        val intent = Intent(this, DatosMetodoPago::class.java)
        startActivity(intent)
    }

    fun eliminar() {
        val mensaje: AlertDialog.Builder = AlertDialog.Builder(this)
        mensaje.setTitle("Sistema de Pagos")
        mensaje.setMessage("¿Seguro de eliminar?")
        mensaje.setCancelable(false)
        mensaje.setPositiveButton("Aceptar") { _, _ ->
            lifecycleScope.launch(Dispatchers.IO) {
                    metodoPagoDao.eliminar(metodoPagoBean)
                    mostrarToast("Método de pago eliminado correctamente")
                    volver()
            }
        }
        mensaje.setNegativeButton("Cancelar") { _, _ -> }
        mensaje.setIcon(android.R.drawable.ic_delete)
        mensaje.show()
    }

    fun validarCampos(): Boolean {
        val nombre = edtNomMetodoPago.text.toString()
        val regex = Regex("^(?=.{3,100}\$)[A-Za-zÑÁÉÍÓÚñáéíóú][A-Za-zÑÁÉÍÓÚñáéíóú]+(?: [A-Za-zÑÁÉÍÓÚñáéíóú]+)*\$")  // Expresión regular que verifica si solo hay letras

        if (nombre.isBlank()) {
            mostrarToast("El campo de nombre no puede estar vacío")
            return false
        }

        if (!regex.matches(nombre)) {
            mostrarToast("El campo de nombre solo puede contener letras")
            return false
        }


        return true
    }

    @SuppressLint("SuspiciousIndentation")
    fun editar() {
        val nuevoNombre = edtNomMetodoPago.text.toString()

        lifecycleScope.launch(Dispatchers.IO) {
            if (validarCampos()) {
                metodoPagoBean.nombreMetodoPago = nuevoNombre
                    metodoPagoDao.actualizar(metodoPagoBean)
                    mostrarToast("Método de pago actualizado correctamente")
                    volver()
            }
        }
    }

    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(appConfig.CONTEXT, mensaje, Toast.LENGTH_SHORT).show()
        }
    }
}


