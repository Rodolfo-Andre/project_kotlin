package com.example.project_kotlin.vistas.platos

import android.content.Intent
import android.os.Bundle
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.adaptador.adaptadores.platos.PlatoAdapter
import com.example.project_kotlin.dao.CategoriaPlatoDao
import com.example.project_kotlin.dao.PlatoDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.Plato
import com.example.project_kotlin.utils.appConfig
import com.example.project_kotlin.vistas.inicio.ConfiguracionVista
import com.example.project_kotlin.vistas.platos.NuevoPlato
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatosPlatos: AppCompatActivity() {
    private lateinit var spnCategoriaPlato:Spinner
    private lateinit var edtBuscarPlato:EditText
    private lateinit var btnBuscar:Button
    private lateinit var rvPlatos: RecyclerView
    private lateinit var btnAgregarPlatos:Button
    private lateinit var btnVolver:Button

    private lateinit var platoDao:PlatoDao
    private lateinit var categoriaPlatosDao: CategoriaPlatoDao
    private lateinit var adaptador : PlatoAdapter


    private var estadoCatFiltro : String = "Seleccionar Categoria"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_platos)

        spnCategoriaPlato = findViewById(R.id.spnCategoriaPlato)
        edtBuscarPlato = findViewById(R.id.edtBuscarPlato)
        btnBuscar= findViewById(R.id.btnConsultarPlato)
        rvPlatos = findViewById(R.id.rvPlatos)
        btnAgregarPlatos = findViewById(R.id.btnAgregarPlatos)
        btnVolver = findViewById(R.id.btnVolverConfi)
        platoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).platoDao()
        categoriaPlatosDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).categoriaPlatoDao()

        cargarCategoria()
        //acciones al boton

        btnVolver.setOnClickListener({volver()})
        btnAgregarPlatos.setOnClickListener({nuevoPlatos()})
        btnBuscar.setOnClickListener({filtrar( edtBuscarPlato)})

        obtenerPlatos()


    }

    fun obtenerPlatos() {
        lifecycleScope.launch(Dispatchers.IO){
            var datos = platoDao.obtenerTodo()

            adaptador = PlatoAdapter(datos)
            rvPlatos.layoutManager= LinearLayoutManager(this@DatosPlatos)
            rvPlatos.adapter = adaptador

        }
    }

    fun filtrar(nombreplato: EditText) {
        lifecycleScope.launch(Dispatchers.IO) {
            val datos = platoDao.obtenerTodo()
            var datosFiltrados: List<Plato> = datos

            val selectedItem = spnCategoriaPlato.selectedItem.toString()
            estadoCatFiltro = selectedItem

            if (selectedItem != "Seleccionar Categoria") {
                datosFiltrados = datosFiltrados.filter { plato -> plato.categoriaPlato.categoria == selectedItem }
            }
            if (!nombreplato.text.toString().isNullOrBlank()) {
                datosFiltrados = datosFiltrados.filter { plato -> plato.nombrePlato == nombreplato.text.toString() }
            }

            withContext(Dispatchers.Main) {
                adaptador.actulizarPlatos(datosFiltrados)
            }
        }
    }



    fun nuevoPlatos(){
        var intent= Intent(this, NuevoPlato::class.java)
        startActivity(intent)
    }

    fun volver(){
        var intent = Intent(this, ConfiguracionVista::class.java)
        startActivity(intent)
    }
    private fun cargarCategoria() {
        lifecycleScope.launch(Dispatchers.IO) {

            // Obtén la lista de categorías de platos desde la base de datos
            var data = categoriaPlatosDao.obtenerTodo()

            var nombreCat =   data.map {  it.categoria }

            val opciones = mutableListOf<String>()
            opciones.add("Seleccionar Categoria")
            opciones.addAll(nombreCat)

            // Crea un ArrayAdapter con los nombres de las categorías de platos
            var adapter = ArrayAdapter(
                this@DatosPlatos,
                android.R.layout.simple_spinner_item,opciones
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Asigna el adaptador al Spinner
            spnCategoriaPlato.adapter = adapter
        }
    }
    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(appConfig.CONTEXT, mensaje, Toast.LENGTH_SHORT).show()
        }
    }
}