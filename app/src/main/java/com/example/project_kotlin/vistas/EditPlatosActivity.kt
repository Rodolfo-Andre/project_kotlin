package com.example.project_kotlin.vistas

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project_kotlin.R
import org.w3c.dom.Text

class EditPlatosActivity:AppCompatActivity() {
    private lateinit var tvCodPlato:TextView
    private lateinit var edtNamePlato:EditText
    private lateinit var edtPrePlato:EditText
    private lateinit var imgPlato:ImageButton
    private  lateinit var spCatplato:Spinner
    private lateinit var btnEditar:Button
    private lateinit var btnCancelar:Button
    private lateinit var btnEliminar:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.platoeditar)
        tvCodPlato=findViewById(R.id.tvCodPlatos)
        edtNamePlato= findViewById(R.id.edtNamePlato)
        edtPrePlato = findViewById(R.id.edtPrePlato)
        imgPlato= findViewById(R.id.imgPlato)
        spCatplato= findViewById(R.id.spCatplato)
        btnEditar= findViewById(R.id.btnEditar)
        btnCancelar= findViewById(R.id.btnCancel)
        btnEliminar = findViewById(R.id.btnEliminar)

        btnCancelar.setOnClickListener{volver()}
    }
    fun volver(){
        var intent= Intent(this,PlatosActivity::class.java)
        startActivity(intent)
    }
}