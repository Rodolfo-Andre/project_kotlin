package com.example.project_kotlin.vistas

import android.os.Bundle
import android.webkit.WebView.FindListener
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project_kotlin.R

class DeletPlatoActivity: AppCompatActivity() {

    private lateinit var tvCodPlato:TextView
    private lateinit var btnCancelar:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.platoeliminar)

        tvCodPlato= findViewById(R.id.tvCodPlato)
        btnCancelar=findViewById(R.id.btnCancelarEliminar)

    }

}