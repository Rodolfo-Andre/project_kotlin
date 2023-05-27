package com.example.project_kotlin.vistas

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project_kotlin.R

class EditCatPlatoActivity:AppCompatActivity() {
    private lateinit var tvCodCategoriaPlatos:TextView
    private lateinit var edtCategoriaNombres:EditText
    private lateinit var btnEditar:Button
    private lateinit var btnCancelar:Button
    private lateinit var btnEliminar:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categoriaplatoeditar)

        tvCodCategoriaPlatos = findViewById(R.id.tvCodCategoriaPlatos)
        edtCategoriaNombres = findViewById(R.id.edtCategoriaNombres)
        btnEditar = findViewById(R.id.btnEditarCategoria)
        btnCancelar= findViewById(R.id.btnCancelarCat)
        btnEliminar = findViewById(R.id.btnEliminarCat)

        btnCancelar.setOnClickListener({Cancelar()})

    }
    fun Cancelar(){
        var intent= Intent(this,CategoriaPlatosActivity::class.java)
        startActivity(intent)
    }
}