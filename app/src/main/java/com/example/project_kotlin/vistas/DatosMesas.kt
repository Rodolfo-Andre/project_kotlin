package com.example.project_kotlin.vistas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.adaptador.adaptadores.ConfiguracionMesasAdapter
import com.example.project_kotlin.dao.MesaDao
import com.example.project_kotlin.db.ComandaDatabase

class DatosMesas : AppCompatActivity(){
    private lateinit var rvMesas : RecyclerView
    private lateinit var btnNuevaMesa : Button
    private lateinit var db: ComandaDatabase
    private lateinit var mesaDao: MesaDao


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.con_mesas)
        btnNuevaMesa = findViewById(R.id.btnNuevaMesaCon)
        rvMesas = findViewById(R.id.rvListadoMesasCon)
        mesaDao = db.mesaDao()
        var datos = mesaDao.obtenerTodo()
        val adaptador = ConfiguracionMesasAdapter(datos)
        rvMesas.layoutManager=LinearLayoutManager(this)
        rvMesas.adapter = adaptador

        btnNuevaMesa.setOnClickListener({adicionar()})
    }
    fun adicionar(){
        var intent = Intent(this, NuevaMesa::class.java)
        startActivity(intent)

    }
}