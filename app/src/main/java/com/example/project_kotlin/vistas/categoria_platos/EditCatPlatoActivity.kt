package com.example.project_kotlin.vistas.categoria_platos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.R
import com.example.project_kotlin.dao.CategoriaPlatoDao
import com.example.project_kotlin.dao.MesaDao
import com.example.project_kotlin.dao.PlatoDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.CategoriaPlato
import com.example.project_kotlin.entidades.Mesa
import com.example.project_kotlin.utils.appConfig
import com.example.project_kotlin.vistas.mesas.DatosMesas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditCatPlatoActivity:AppCompatActivity() {
    private lateinit var tvCodCategoriaPlatos:TextView
    private lateinit var edtCategoriaNombres:EditText
    private lateinit var btnEditar:Button
    private lateinit var btnCancelar:Button
    private lateinit var btnEliminar:Button

    private lateinit var cateDAO: CategoriaPlatoDao
    private lateinit var prodDAO: PlatoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categoriaplatoeditar)

        tvCodCategoriaPlatos = findViewById(R.id.tvCodCategoriaPlatos)
        edtCategoriaNombres = findViewById(R.id.edtCategoriaNombres)
        btnEditar = findViewById(R.id.btnEditarCategoria)
        btnCancelar= findViewById(R.id.btnCancelarCat)
        btnEliminar = findViewById(R.id.btnEliminarCat)
        cateDAO = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).categoriaPlatoDao()
        prodDAO = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).platoDao()

        btnEditar.setOnClickListener({Editar()})
        btnCancelar.setOnClickListener({Cancelar()})
        btnEliminar.setOnClickListener({Eliminar()})

        val cate = intent.getSerializableExtra("categoriaPlato") as CategoriaPlato
        tvCodCategoriaPlatos.setText(cate.id)
        edtCategoriaNombres.setText(cate.categoria)

    }
    fun Cancelar(){
        var intent= Intent(this, CategoriaPlatosActivity::class.java)
        startActivity(intent)
    }

    fun Editar() {
        val idCategoria = tvCodCategoriaPlatos.text.toString()
        lifecycleScope.launch(Dispatchers.IO) {

                if (validarCampos()) {
                    val nombreCategoria = edtCategoriaNombres.text.toString()
                    val catego = CategoriaPlato(idCategoria, nombreCategoria)
                    cateDAO.actualizar(catego)
                    mostrarToast("Categoria actualizada correctamente")
                    Volver()
                }

        }
    }

    fun Eliminar() {
        val codCate = tvCodCategoriaPlatos.text.toString()
        val mensaje: AlertDialog.Builder = AlertDialog.Builder(this)
        mensaje.setTitle("Sistema comandas")
        mensaje.setMessage("¿Seguro de eliminar?")
        mensaje.setCancelable(false)
        mensaje.setPositiveButton("Aceptar") { _, _ ->
            lifecycleScope.launch(Dispatchers.IO) {
                //Validar de comandas
                val validarCategoria = prodDAO.obtenerPlatosPorCategoria(codCate)
                if(validarCategoria.isEmpty()){
                    val eliminar = cateDAO.obtenerPorId(codCate)
                    cateDAO.eliminar(eliminar)
                    mostrarToast("Categoria eliminada correctamente")
                    Volver()
                }else{
                    mostrarToast("La categoría que tiene productos registrados no puede ser eliminado")
                }


            }
        }
        mensaje.setNegativeButton("Cancelar") { _, _ -> }
        mensaje.setIcon(android.R.drawable.ic_delete)
        mensaje.show()
    }

    fun Volver() {
        val intent = Intent(this, CategoriaPlatosActivity::class.java)
        startActivity(intent)
    }

    fun validarCampos(): Boolean {
        val cateNombre = edtCategoriaNombres.text.toString()
        val regex = Regex("[0-9]")

        if (cateNombre.isEmpty() || regex.containsMatchIn(cateNombre)) {
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
}