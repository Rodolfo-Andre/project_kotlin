package com.example.project_kotlin.vistas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.project_kotlin.R
import com.example.project_kotlin.dao.MesaDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.Mesa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NuevaMesa  : AppCompatActivity() {
    //private val database by lazy { t }
    //private val mesaDao by lazy { database.mesaDao() }

    private lateinit var btnVolverListadoMesa : Button
    private lateinit var btnAgregar: Button
    private lateinit var edCantidadAsientos : EditText
    private lateinit var db: ComandaDatabase
    private lateinit var mesaDao: MesaDao

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agregar_mesa)

        btnVolverListadoMesa = findViewById(R.id.btnCancelarAgregarMesa)

        db = Room.databaseBuilder(
            this, ComandaDatabase::class.java, "comanda_database").build()

        btnAgregar = findViewById(R.id.btnNuevaMesaA)
        edCantidadAsientos = findViewById(R.id.edtCanAsientosMesaA)
        btnVolverListadoMesa = findViewById(R.id.btnCancelarAgregarMesa)

        btnVolverListadoMesa.setOnClickListener({volver()})
        btnAgregar.setOnClickListener({agregarMesa(db)})
    }
    fun agregarMesa(room: ComandaDatabase){
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()

        lifecycleScope.launch{
            withContext(Dispatchers.IO) {
                var cantidad = edCantidadAsientos.text.toString().toInt()
                var bean = Mesa(cantidadAsientos = cantidad, estadoMesa = "Libre")

                mesaDao = db.mesaDao()
                mesaDao.guardar(bean)
                System.out.println(bean)
                System.out.println(mesaDao.obtenerTodo())


            }
        }




    }
    fun volver(){
        var intent = Intent(this, DatosMesas::class.java)
        startActivity(intent)
    }
}