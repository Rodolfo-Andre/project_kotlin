package com.example.project_kotlin.vistas

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.project_kotlin.R

class DeletCatPlatoActivity:AppCompatActivity() {

    private lateinit var tvCodCategoriaPlato:EditText
    private lateinit var btnCancelar:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categoriaplatoeliminar)

        tvCodCategoriaPlato= findViewById(R.id.tvCodCategoriaPlato)
        btnCancelar = findViewById(R.id.btnVolver)

    }


}