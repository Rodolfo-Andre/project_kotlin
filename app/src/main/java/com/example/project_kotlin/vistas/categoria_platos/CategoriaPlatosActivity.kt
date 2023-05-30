package com.example.project_kotlin.vistas.categoria_platos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R

class CategoriaPlatosActivity: AppCompatActivity() {
    private lateinit var edtBuscarCategoriaPlato: EditText
    private lateinit var btnConsultarCategoria: Button
    private lateinit var rvCategoria: RecyclerView
    private lateinit var btnAgregarCategoria: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_categoriaplatos)

        edtBuscarCategoriaPlato = findViewById(R.id.edtBuscarCategoriaPlato)
        btnConsultarCategoria = findViewById(R.id.btnConsultarCategoria)
        rvCategoria = findViewById(R.id.rvCategoria)
        btnAgregarCategoria = findViewById(R.id.btnAgregarCategoriaPlatos)

        btnAgregarCategoria.setOnClickListener({ agregar() })
    }

fun agregar(){
    var intent= Intent(this, NewCatPlatoActivity::class.java)
    startActivity(intent)
}

}