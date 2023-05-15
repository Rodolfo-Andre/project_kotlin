package com.example.project_kotlin.vistas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.ComandaApplication
import com.example.project_kotlin.R
import com.example.project_kotlin.controladores.MesaControlador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), OnClickListener {
    lateinit var btnAperturar: Button
    lateinit var mesaControlador: MesaControlador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.caja_activity)

        btnAperturar = findViewById(R.id.btnAperturar)
        btnAperturar.setOnClickListener(this)
        mesaControlador = MesaControlador(aplicacion = application as ComandaApplication)
    }

    override fun onClick(v: View?) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                System.out.println("Tamaño ${mesaControlador.listado().size}")
            }
        }
    }
}