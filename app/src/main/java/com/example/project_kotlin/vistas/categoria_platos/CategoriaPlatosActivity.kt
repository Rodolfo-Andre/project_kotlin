package com.example.project_kotlin.vistas.categoria_platos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.adaptador.adaptadores.categoria_plato.CategoriaPlatoAdapter
import com.example.project_kotlin.adaptador.adaptadores.mesas.ConfiguracionMesasAdapter
import com.example.project_kotlin.dao.CategoriaPlatoDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.CategoriaPlato
import com.example.project_kotlin.entidades.Mesa
import com.example.project_kotlin.utils.appConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriaPlatosActivity: AppCompatActivity() {
    private lateinit var rvCategoria: RecyclerView
    private lateinit var btnAgregarCategoria: Button
    private lateinit var cateDao: CategoriaPlatoDao
    private lateinit var adaptador : CategoriaPlatoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_categoriaplatos)


        cateDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).categoriaPlatoDao()
        rvCategoria = findViewById(R.id.rvCategoria)
        btnAgregarCategoria = findViewById(R.id.btnAgregarCategoriaPlatos)
        rvCategoria = findViewById(R.id.rvCategoria)

        btnAgregarCategoria.setOnClickListener({agregarVista()})
        obtenerCategorias()
    }

    fun agregarVista(){
        var intent= Intent(this, NewCatPlatoActivity::class.java)
        startActivity(intent)
    }

    fun obtenerCategorias() {
        lifecycleScope.launch(Dispatchers.IO){
            var datos = cateDao.obtenerTodo()
            val datosCopia = ArrayList<CategoriaPlato>(datos)

            withContext(Dispatchers.Main) {
                adaptador = CategoriaPlatoAdapter(datosCopia)
                rvCategoria.layoutManager = LinearLayoutManager(this@CategoriaPlatosActivity)
                rvCategoria.adapter = adaptador
            }

        }
    }







}