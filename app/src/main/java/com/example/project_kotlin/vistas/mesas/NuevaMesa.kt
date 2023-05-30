package com.example.project_kotlin.vistas.mesas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.project_kotlin.R
import com.example.project_kotlin.dao.MesaDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.Mesa
import com.example.project_kotlin.utils.appConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevaMesa : AppCompatActivity() {

    private lateinit var btnVolverListadoMesa: Button
    private lateinit var btnAgregar: Button
    private lateinit var edCantidadAsientos: EditText
    private lateinit var db: ComandaDatabase
    private lateinit var mesaDao: MesaDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agregar_mesa)

        btnVolverListadoMesa = findViewById(R.id.btnCancelarAgregarMesa)
        db = Room.databaseBuilder(this, ComandaDatabase::class.java, "comanda_database").build()
        mesaDao = db.mesaDao()
        btnAgregar = findViewById(R.id.btnNuevaMesaA)
        edCantidadAsientos = findViewById(R.id.edtCanAsientosMesaA)
        btnVolverListadoMesa = findViewById(R.id.btnCancelarAgregarMesa)
        btnVolverListadoMesa.setOnClickListener { volver() }
        btnAgregar.setOnClickListener { agregarMesa(db) }
    }

    fun agregarMesa(room: ComandaDatabase) {
        lifecycleScope.launch(Dispatchers.IO) {
            if (validarCampos()) {
                val cantidad = edCantidadAsientos.text.toString().toInt()
                val bean = Mesa(cantidadAsientos = cantidad, estadoMesa = "Libre")
                mesaDao.guardar(bean)
                mostrarToast("Mesa agregada correctamente")
                volver()
            }
        }
    }
    //Ejemplo de cómo pueden validar sus campos en Kotlin
    fun validarCampos(): Boolean {
        val cantidad = edCantidadAsientos.text.toString().toIntOrNull()
        if (cantidad == null || cantidad !in 1..9) {
            mostrarToast("La cantidad de asientos debe ser un número de 1 al 9")
            return false
        }
        return true
    }

    fun volver() {
        val intent = Intent(this, DatosMesas::class.java)
        startActivity(intent)
    }

    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(appConfig.CONTEXT, mensaje, Toast.LENGTH_SHORT).show()
        }
    }
}