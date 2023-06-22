package com.example.project_kotlin.vistas.caja_registradora

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button

import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

import com.example.project_kotlin.R
import com.example.project_kotlin.dao.CajaDao
import com.example.project_kotlin.dao.EstablecimientoDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.Caja
import com.example.project_kotlin.entidades.Establecimiento


import com.example.project_kotlin.utils.appConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import kotlin.collections.map


class NuevaCaja : AppCompatActivity() {

    private lateinit var btnRegresar: Button
    private lateinit var btnRegistrarCaja: Button
    private lateinit var spnEstablecimiento: Spinner
    private lateinit var cajaDao: CajaDao
    private lateinit var establecimientoDao: EstablecimientoDao

    private var establecimientoDefault : String = "Seleccionar Establecimiento"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agregar_caja)

        btnRegresar = findViewById(R.id.btnRegresarlistaCajas)
        cajaDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).cajaDao()
        btnRegistrarCaja = findViewById(R.id.btnAgregarCaja)
        spnEstablecimiento = findViewById(R.id.spnEstablecimiento)

        btnRegresar.setOnClickListener { volver() }
        btnRegistrarCaja.setOnClickListener { agregarCaja() }

        establecimientoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).establecimientoDao()

        cargarEstablecimiento()
    }

    private fun cargarEstablecimiento() {
        lifecycleScope.launch(Dispatchers.IO) {

            // Obtén la lista de categorías de platos desde la base de datos
            var data = establecimientoDao.obtener()

            var nombreEstablecimientoList =   data.map {  it.nomEstablecimiento }

            val opciones = mutableListOf<String>()
            opciones.add("Seleccionar Establecimiento")
            opciones.addAll(nombreEstablecimientoList)

            // Crea un ArrayAdapter con los nombres de las categorías de establecimientos
            var adapter = ArrayAdapter(
                this@NuevaCaja,
                android.R.layout.simple_spinner_item,opciones
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Asigna el adaptador al Spinner
            spnEstablecimiento.adapter = adapter
        }
    }




    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(this@NuevaCaja, mensaje, Toast.LENGTH_SHORT).show()
        }
    }

    private fun volver() {
        val intent = Intent(this, DatosCajas::class.java)
        startActivity(intent)
    }


    private fun agregarCaja() {
        lifecycleScope.launch(Dispatchers.IO) {
            val establecimientoId = spnEstablecimiento.selectedItemPosition
            if (establecimientoId == 0) {
                mostrarToast("Seleccione un establecimiento")
            } else {
                val establecimiento = establecimientoDao.obtenerPorId(establecimientoId.toLong())

                val nuevaCaja = Caja()
                nuevaCaja.establecimiento = establecimiento

                cajaDao.guardar(nuevaCaja)
                mostrarToast("Caja registrada")
                volver()
            }
        }
    }








    //
}







/*
    fun agregarCaja() {
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



    fun validarCampos(): Boolean {
        val cantidad = edCantidadAsientos.text.toString().toIntOrNull()
        if (cantidad == null || cantidad !in 1..9) {
            mostrarToast("La cantidad de asientos debe ser un número de 1 al 9")
            return false
        }
        return true
    }

    */