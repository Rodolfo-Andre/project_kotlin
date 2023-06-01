package com.example.project_kotlin.vistas.caja_registradora

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.R
import com.example.project_kotlin.dao.CajaDao
import com.example.project_kotlin.dao.EstablecimientoDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.Caja
import com.example.project_kotlin.entidades.Mesa
import com.example.project_kotlin.utils.appConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ActualizarCajas  : AppCompatActivity(){


    private lateinit var btnVolverListadoCaja: Button
    private lateinit var btnEditarCaja: Button
    private lateinit var btnEliminarCaja: Button
    private lateinit var edtCodigoCajaEdit: EditText
    private lateinit var spnEstablecimientoEdit: Spinner
    private lateinit var CajaDao: CajaDao
    private lateinit var establecimientoDao: EstablecimientoDao
    private lateinit var cajabean: Caja


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modificar_caja)

        btnVolverListadoCaja = findViewById(R.id.btnVolverListadoCaja)
        edtCodigoCajaEdit = findViewById(R.id.edtCodigoCajaEdit)
        CajaDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).cajaDao()
        btnEditarCaja = findViewById(R.id.btnEditarCaja)
        btnEliminarCaja = findViewById(R.id.btnEliminarCaja)
        spnEstablecimientoEdit = findViewById(R.id.spnEstablecimientoEdit)

        btnVolverListadoCaja.setOnClickListener { volver() }
        btnEditarCaja.setOnClickListener { actualizarCajas() }
        btnEliminarCaja.setOnClickListener { Eliminar()}
        establecimientoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).establecimientoDao()



        cajabean = intent.getSerializableExtra("caja") as Caja

        cargarEstablecimiento()
        edtCodigoCajaEdit.setText(cajabean.id.toString())

    }



    private fun cargarEstablecimiento() {
        lifecycleScope.launch(Dispatchers.IO) {
            val establecimientoList = establecimientoDao.obtener()
            val nombreEstablecimientoList = establecimientoList.map { it.nombreEstablecimiento }

            val opciones = mutableListOf<String>()
            opciones.add("Seleccionar Establecimiento")
            opciones.addAll(nombreEstablecimientoList)

            val adapter = ArrayAdapter(
                this@ActualizarCajas,
                android.R.layout.simple_spinner_item,
                opciones
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spnEstablecimientoEdit.adapter = adapter

            val seleccion = establecimientoList.indexOfFirst { it.id == cajabean.establecimiento?.id } + 1
            spnEstablecimientoEdit.setSelection(seleccion)
        }
    }


    fun actualizarCajas() {
        lifecycleScope.launch(Dispatchers.IO) {
            val establecimientoId = spnEstablecimientoEdit.selectedItemPosition
            if (establecimientoId == 0) {
                mostrarToast("Seleccione un establecimiento")
            } else {
                val establecimiento = establecimientoDao.obtenerPorId(establecimientoId.toLong())

                cajabean.establecimiento = establecimiento

                CajaDao.actualizar(cajabean)
                mostrarToast("Caja actualizada correctamente")
                volver()
            }
        }
    }


    fun Eliminar() {


        val mensaje: AlertDialog.Builder = AlertDialog.Builder(this)
        mensaje.setTitle("Sistema comandas")
        mensaje.setMessage("¿Seguro de eliminar?")
        mensaje.setCancelable(false)
        mensaje.setPositiveButton("Aceptar") { _, _ ->
            lifecycleScope.launch(Dispatchers.IO) {
                CajaDao.eliminar(cajabean)
                mostrarToast("Caja eliminada")
                volver()
            }
        }
        mensaje.setNegativeButton("Cancelar") { _, _ -> }
        mensaje.setIcon(android.R.drawable.ic_delete)
        mensaje.show()
    }


    fun volver() {
        val intent = Intent(this, DatosCajas::class.java)
        startActivity(intent)
    }

    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(appConfig.CONTEXT, mensaje, Toast.LENGTH_SHORT).show()
        }
    }
}