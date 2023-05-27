package com.example.project_kotlin.vistas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.ComandaApplication
import com.example.project_kotlin.R
import com.example.project_kotlin.controladores.MesaControlador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
   /* lateinit var btnAperturar: Button
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
    }*/

    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnIngresar:Button
    private lateinit var btnRecuparar:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtEmail= findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        btnIngresar = findViewById(R.id.btnIngresar)
        btnRecuparar = findViewById(R.id.btnRecuperar)
    }



}