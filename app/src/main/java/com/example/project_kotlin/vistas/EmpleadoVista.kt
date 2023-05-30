package com.example.project_kotlin.vistas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.project_kotlin.R

class EmpleadoVista:AppCompatActivity() {

    private lateinit var edtNomUsu:EditText
    private lateinit var edtApeUsu:EditText
    private lateinit var edtDniUsu:EditText
    private lateinit var edtCorreoUsu:EditText
    private lateinit var edtTelfUsu:EditText
    private lateinit var spnUsu:Spinner
    private lateinit var btnNuevoUsu:Button
    private lateinit var btnCancelarUsu:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agregar_usu)

        edtNomUsu = findViewById(R.id.edtNomUsu)
        edtApeUsu = findViewById(R.id.edtApeUsu)
        edtDniUsu = findViewById(R.id.edtDniUsu)
        edtCorreoUsu = findViewById(R.id.edtCorreoUsu)
        edtTelfUsu = findViewById(R.id.edtTelfUsu)
        spnUsu = findViewById(R.id.spnUsu)
        btnNuevoUsu = findViewById(R.id.btnNuevoUsu)
        btnCancelarUsu = findViewById(R.id.btnCancelarUsu)


    }


}