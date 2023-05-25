package com.example.project_kotlin.vistas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.project_kotlin.ComandaApplication
import com.example.project_kotlin.R
import com.example.project_kotlin.adaptador.adaptadores.ConfiguracionMesasAdapter
import com.example.project_kotlin.dao.MesaDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.Mesa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatosMesas : AppCompatActivity(){
    private lateinit var rvMesas : RecyclerView
    private lateinit var btnNuevaMesa : Button
    private lateinit var db: ComandaDatabase
    private lateinit var mesaDao: MesaDao


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.con_mesas)
        btnNuevaMesa = findViewById(R.id.btnNuevaMesaCon)
        rvMesas = findViewById(R.id.rvListadoMesasCon)
        db = Room.databaseBuilder(
            this, ComandaDatabase::class.java, "comanda_database").build()
        mesaDao = db.mesaDao()
        obtenerMesas(db)



        btnNuevaMesa.setOnClickListener({adicionar()})
    }
    fun obtenerMesas(room: ComandaDatabase){
        lifecycleScope.launch{
            withContext(Dispatchers.IO) {
                var datos = mesaDao.obtenerTodo()
                val datosConvertidos: ArrayList<Mesa> = ArrayList(datos)
                val adaptador = ConfiguracionMesasAdapter(datosConvertidos)
                rvMesas.layoutManager=LinearLayoutManager(this@DatosMesas)
                rvMesas.adapter = adaptador

            }


        }
    }
    fun adicionar(){
        var intent = Intent(this, NuevaMesa::class.java)
        startActivity(intent)

    }
}