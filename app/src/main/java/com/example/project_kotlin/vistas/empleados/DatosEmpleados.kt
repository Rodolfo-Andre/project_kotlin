package com.example.project_kotlin.vistas.empleados

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.adaptador.adaptadores.empleado.EmpleadoAdapter
import com.example.project_kotlin.adaptador.adaptadores.mesas.ConfiguracionMesasAdapter
import com.example.project_kotlin.dao.CargoDao
import com.example.project_kotlin.dao.EmpleadoDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.utils.appConfig
import com.example.project_kotlin.vistas.inicio.ConfiguracionVista
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatosEmpleados: AppCompatActivity() {
    private lateinit var btnVolver : Button
    private lateinit var  btnAgregar : Button
    private lateinit var empleadoDao : EmpleadoDao
    private lateinit var cargoDao : CargoDao
    private lateinit var adaptador : EmpleadoAdapter
    private lateinit var spCargos : Spinner
    private lateinit var rvEmpleados : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_usuarios)
        btnAgregar = findViewById(R.id.btnNuevoEmpleadoCon)
        btnVolver = findViewById(R.id.btnRegresarIndexEmpleado)
        spCargos = findViewById(R.id.spnEmpleadoFiltro)
        rvEmpleados = findViewById(R.id.rvEmpleadosConfiguracion)
        btnVolver.setOnClickListener({volver()})
        btnAgregar.setOnClickListener({agregar()})
        empleadoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).empleadoDao()
        cargoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).cargoDao()
        cargarCargos()
        obtenerEmpleados()
    }
    /*private fun cargarCategoria() {
        lifecycleScope.launch(Dispatchers.IO) {

            // Obtén la lista de categorías de platos desde la base de datos
            val data = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).categoriaPlatoDao().obtenerTodo()

            // Crea un ArrayAdapter con los nombres de las categorías de platos
            val adapter = ArrayAdapter(
                this@NuevoPlato,
                android.R.layout.simple_spinner_item,
                data.map { it.categoria }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Asigna el adaptador al Spinner
            spCategoria.adapter = adapter
        }
    }*/
    fun cargarCargos(){
        lifecycleScope.launch(Dispatchers.IO){
            val cargos = cargoDao.obtenerTodo()
            val nombresCargos = cargos.map { it.cargo }
            val adapter = ArrayAdapter(this@DatosEmpleados, android.R.layout.simple_spinner_item, nombresCargos)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spCargos.adapter = adapter

        }
    }
    fun obtenerEmpleados(){
        lifecycleScope.launch(Dispatchers.IO){
            var datos = empleadoDao.obtenerTodo()

            adaptador = EmpleadoAdapter(datos)
            rvEmpleados.layoutManager= LinearLayoutManager(this@DatosEmpleados)
            rvEmpleados.adapter = adaptador

        }
    }
    fun volver(){
        var intent = Intent(this, ConfiguracionVista::class.java)
        startActivity(intent)
    }
    fun agregar(){
        var intent = Intent(this, NuevoEmpleado::class.java)
        startActivity(intent)
    }
    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(appConfig.CONTEXT, mensaje, Toast.LENGTH_SHORT).show()
        }
    }
}