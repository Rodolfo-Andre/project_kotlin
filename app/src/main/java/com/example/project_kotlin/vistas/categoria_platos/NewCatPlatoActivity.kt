package com.example.project_kotlin.vistas.categoria_platos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewCatPlatoActivity: AppCompatActivity() {

    private lateinit var edtCategoriaNombre:EditText
    private lateinit var btnAgregar:Button
    private lateinit var btnCancelar:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categoriaplatoregistrar)

        edtCategoriaNombre= findViewById(R.id.edtCategoriaNombre)
        btnAgregar = findViewById(R.id.btnAgregarCategoria)
        btnCancelar = findViewById(R.id.btnCancelarCategoria)

        btnCancelar.setOnClickListener({Cancelar()})
    }

    fun Cancelar(){
        var intent= Intent(this, CategoriaPlatosActivity::class.java)
        startActivity(intent)
    }



    /*fun AgregarMesa(){
        lifecycleScope.launch(Dispatchers.IO){
            if(validarCampos())
        }
    }*/

    /*fun validarCampos(): Boolean {
        val cantidad = edCantidadAsientos.text.toString().toIntOrNull()
        if (cantidad == null || cantidad !in 1..9) {
            mostrarToast("La cantidad de asientos debe ser un número de 1 al 9")
            return false
        }
        return true
    }*/

}
