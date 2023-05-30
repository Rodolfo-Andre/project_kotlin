package com.example.project_kotlin.vistas

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.project_kotlin.R
import com.example.project_kotlin.utils.appConfig
import com.example.project_kotlin.vistas.mesas.DatosMesas

class ConfiguracionVista:AppCompatActivity() {

    private lateinit var cvEmpleados:CardView
    private lateinit var cvPlatos:CardView
    private lateinit var cvMesas:CardView
    private lateinit var cvClientes:CardView
    private lateinit var cvEstablecimiento:CardView
    private lateinit var cvComandas:CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuracion)

        cvEmpleados = findViewById(R.id.cvEmpleados)
        cvPlatos = findViewById(R.id.cvPlatos)
        cvMesas = findViewById(R.id.cvMesas)
        cvClientes = findViewById(R.id.cvClientes)
        cvEstablecimiento = findViewById(R.id.cvEstablecimiento)
        cvComandas = findViewById(R.id.cvComandas)

        cvEmpleados.setOnClickListener({vincularEmpleados()})
        cvPlatos.setOnClickListener({vincularPlatos()})
        cvMesas.setOnClickListener({vincularMesas()})
        cvClientes.setOnClickListener({vincularClientes()})
        cvEstablecimiento.setOnClickListener({vincularEstablecimiento()})
        cvComandas.setOnClickListener({vincularComandas()})


    }

     fun vincularEmpleados() {

         var intent = Intent(this, EmpleadoVista::class.java)
         startActivity(intent)

    }

    fun vincularPlatos() {

        var intent = Intent(this, PlatosActivity::class.java)
        startActivity(intent)

    }

    //VISTA MESA --PARTE DE FABIAN
    fun vincularMesas() {

        var intent = Intent(appConfig.CONTEXT, DatosMesas::class.java)
        startActivity(intent)

    }

    //falta
    fun vincularClientes() {

        var intent = Intent(this, ClientesVista::class.java)
        startActivity(intent)

    }

    //falta
    fun vincularEstablecimiento() {

        var intent = Intent(this, EstablecimientoVista::class.java)
        startActivity(intent)

    }

    //falta
    fun vincularComandas(){

        var intent = Intent(this, ComandasVista::class.java)
        startActivity(intent)

    }



}