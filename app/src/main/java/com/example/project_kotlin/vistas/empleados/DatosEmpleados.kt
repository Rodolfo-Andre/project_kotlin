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
import com.example.project_kotlin.dao.CargoDao
import com.example.project_kotlin.dao.EmpleadoDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.utils.appConfig
import com.example.project_kotlin.vistas.inicio.ConfiguracionVista
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        spCargos = findViewById(R.id.spnCargoEmpleadoE)
        rvEmpleados = findViewById(R.id.rvEmpleadosConfiguracion)
        btnVolver.setOnClickListener({volver()})
        btnAgregar.setOnClickListener({agregar()})
        empleadoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).empleadoDao()
        cargoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).cargoDao()
        cargarCargos()
        obtenerEmpleados()
    }

    fun cargarCargos(){
        lifecycleScope.launch(Dispatchers.IO){
            val cargos = cargoDao.obtenerTodo()
            val nombresCargos = cargos.map { it.cargo }
            val adapter = ArrayAdapter(this@DatosEmpleados, android.R.layout.simple_spinner_item, nombresCargos)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spCargos.adapter = adapter

        }
    }
    fun obtenerEmpleados() {
        lifecycleScope.launch(Dispatchers.IO) {
            val datos = empleadoDao.obtenerTodoLiveData()
            withContext(Dispatchers.Main) {
                datos.observe(this@DatosEmpleados) { empleados ->
                    adaptador = EmpleadoAdapter(empleados)
                    rvEmpleados.layoutManager = LinearLayoutManager(this@DatosEmpleados)
                    rvEmpleados.adapter = adaptador

                }
            }
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