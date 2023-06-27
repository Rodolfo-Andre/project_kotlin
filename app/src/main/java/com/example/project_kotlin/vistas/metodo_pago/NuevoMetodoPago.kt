package com.example.project_kotlin.vistas.metodo_pago

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.dao.MetodoPagoDao
import com.example.project_kotlin.R
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.MetodoPago
import com.example.project_kotlin.utils.appConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevoMetodoPago:AppCompatActivity() {

    private lateinit var edtNomPago: EditText
    private lateinit var btnAgregarPago: Button
    private lateinit var btnVolverListadoPago: Button
    private lateinit var metodoPagoDao: MetodoPagoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agregar_pago)

        btnVolverListadoPago = findViewById(R.id.btnCancelarPago)
        metodoPagoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).metodoPagoDao()
        btnAgregarPago = findViewById(R.id.btnAgregarMetPago)
        edtNomPago = findViewById(R.id.edtNomPago)

        btnVolverListadoPago.setOnClickListener { volver() }
        btnAgregarPago.setOnClickListener { agregarPago() }



    }

    private fun agregarPago() {
        lifecycleScope.launch(Dispatchers.IO){
            if (validarCampos()){
                val nombre = edtNomPago.text.toString()
                val bean = MetodoPago(nombreMetodoPago = nombre)
                metodoPagoDao.registrar(bean)
                mostrarToast("Metodo de Pago agregado correctamente")
                volver()
            }
        }
    }

    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(appConfig.CONTEXT, mensaje, Toast.LENGTH_SHORT).show()
        }
    }

    private fun validarCampos(): Boolean {
        val nombre = edtNomPago.text.toString()
        val regex = Regex("^(?=.{3,100}\$)[A-Za-zÑÁÉÍÓÚñáéíóú][A-Za-zÑÁÉÍÓÚñáéíóú]+(?: [A-Za-zÑÁÉÍÓÚñáéíóú]+)*\$")  // Expresión regular que verifica si solo hay letras

        if (!regex.matches(nombre)) {
            mostrarToast("El campo de nombre solo puede contener letras")
            return false
        }

        if (nombre.isBlank()) {
            mostrarToast("El campo de nombre no puede estar vacío")
            return false
        }

        return true
    }

    private fun volver() {
        val intent = Intent(this, DatosMetodoPago::class.java)
        startActivity(intent)
    }

}