package com.example.project_kotlin.vistas.platos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.project_kotlin.R

class NewPlatoActivity:AppCompatActivity() {

    private lateinit var edtNombrePlato:EditText
    private lateinit var edtPrecioPlato:EditText
    private lateinit var imgButon:ImageButton
    private lateinit var spCategoria:Spinner
    private lateinit var btnAgregar:Button
    private lateinit var btnCancelar:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.platoregistrar)

        edtNombrePlato= findViewById(R.id.edtNombrePlato)
        edtPrecioPlato= findViewById(R.id.edtPrecioPlato)
        imgButon= findViewById(R.id.imagenPlato)
        spCategoria=findViewById(R.id.spCategoria)
        btnAgregar=findViewById(R.id.btnAgregar)
        btnCancelar=findViewById(R.id.btnCancelar)

        //btnAgregar.setOnClickListener({Agregar})
        btnCancelar.setOnClickListener({Cancelar()})
    }


    fun Cancelar(){
        var intent= Intent(this, PlatosActivity::class.java)
        startActivity(intent)
    }



}