package com.example.project_kotlin.vistas

import android.content.Intent
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.Spinner

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R

class PlatosActivity: AppCompatActivity() {
    private lateinit var spnCategoriaPlato:Spinner
    private lateinit var edtBuscarPlato:EditText
    private lateinit var btnBuscar:Button
    private lateinit var rvPlatos: RecyclerView
    private lateinit var btnAgregarPlatos:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_platos)

        spnCategoriaPlato = findViewById(R.id.spnCategoriaPlato)
        edtBuscarPlato = findViewById(R.id.edtBuscarPlato)
        btnBuscar= findViewById(R.id.btnConsultarPlato)
        rvPlatos = findViewById(R.id.rvPlatos)
        btnAgregarPlatos = findViewById(R.id.btnAgregarPlatos)
        //acciones al boton

        btnAgregarPlatos.setOnClickListener({nuevoPlatos()})
    }

    fun nuevoPlatos(){
        var intent= Intent(this,NewPlatoActivity::class.java)
        startActivity(intent)
    }



}