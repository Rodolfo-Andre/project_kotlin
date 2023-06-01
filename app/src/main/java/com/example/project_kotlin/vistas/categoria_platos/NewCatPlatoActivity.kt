package com.example.project_kotlin.vistas.categoria_platos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.R
import com.example.project_kotlin.dao.CategoriaPlatoDao
import com.example.project_kotlin.dao.MesaDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.CategoriaPlato
import com.example.project_kotlin.utils.appConfig
import com.example.project_kotlin.vistas.mesas.DatosMesas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewCatPlatoActivity: AppCompatActivity() {

    private lateinit var edtCategoriaNombre:EditText
    private lateinit var btnAgregar:Button
    private lateinit var btnCancelar:Button
    private lateinit var cateDao: CategoriaPlatoDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categoriaplatoregistrar)

        cateDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).categoriaPlatoDao()
        edtCategoriaNombre= findViewById(R.id.edtCategoriaNombre)
        btnAgregar = findViewById(R.id.btnAgregarCategoria)
        btnCancelar = findViewById(R.id.btnCancelarCategoria)
        btnAgregar.setOnClickListener({AgregarCategoria()})
        btnCancelar.setOnClickListener({volverIndex()})
    }

    fun Cancelar(){
        var intent= Intent(this, CategoriaPlatosActivity::class.java)
        startActivity(intent)
    }



    fun AgregarCategoria(){
        lifecycleScope.launch(Dispatchers.IO){
            if(validarCampos()){
                val codigo = CategoriaPlato.generarCodigo(cateDao.obtenerTodo())
                val nombre = edtCategoriaNombre.text.toString()
                val bean = CategoriaPlato(id = codigo, categoria = nombre)
                cateDao.guardar(bean)
                mostrarToast("Categoría agregada correctamente")
                volverIndex()
            }
        }
    }

    fun validarCampos(): Boolean {
        val cateNombre = findViewById<EditText>(R.id.edtCategoriaNombre)
        val nombreCategoria = cateNombre.text.toString()
        val regex = Regex("[0-9]")

        if (cateNombre.text.toString().isEmpty() || regex.containsMatchIn(nombreCategoria)) {
            mostrarToast("Ingrese categoría válida")
            return false
        }
        return true
    }

    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(appConfig.CONTEXT, mensaje, Toast.LENGTH_SHORT).show()
        }
    }

    fun volverIndex() {
        val intent = Intent(this, CategoriaPlatosActivity::class.java)
        startActivity(intent)
    }

}
