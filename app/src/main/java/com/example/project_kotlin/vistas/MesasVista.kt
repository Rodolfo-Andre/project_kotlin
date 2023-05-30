package com.example.project_kotlin.vistas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R

class MesasVista :AppCompatActivity(){

    private lateinit var spnFiltrarEstadoMesas:Spinner
    private lateinit var edtBuscarMesas:EditText
    private lateinit var rvListadoMesasCon:RecyclerView
    private lateinit var btnNuevaMesaCon:Button
    private lateinit var btnRegresarIndexMesas:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.con_mesas)

        spnFiltrarEstadoMesas = findViewById(R.id.spnFiltrarEstadoMesas)
        edtBuscarMesas = findViewById(R.id.edtBuscarMesas)
        rvListadoMesasCon = findViewById(R.id.rvListadoMesasCon)
        btnNuevaMesaCon = findViewById(R.id.btnNuevaMesaCon)
        btnRegresarIndexMesas = findViewById(R.id.btnRegresarIndexMesas)

    }

}