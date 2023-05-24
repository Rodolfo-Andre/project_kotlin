package com.example.project_kotlin.vistas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.project_kotlin.R

class NuevaMesa  : AppCompatActivity() {
    private lateinit var btnVolverListadoMesa : Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agregar_mesa)
        btnVolverListadoMesa = findViewById(R.id.btnCancelarAgregarMesa)
        btnVolverListadoMesa.setOnClickListener({volver()})
    }
    fun volver(){
        var intent = Intent(this, DatosMesas::class.java)
        startActivity(intent)
    }
}