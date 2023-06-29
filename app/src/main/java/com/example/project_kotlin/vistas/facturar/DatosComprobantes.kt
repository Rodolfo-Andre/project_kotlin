package com.example.project_kotlin.vistas.facturar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.adaptador.adaptadores.comprobantes.ComprobantesAdapter
import com.example.project_kotlin.adaptador.adaptadores.mesas.ConfiguracionMesasAdapter
import com.example.project_kotlin.dao.CajaDao
import com.example.project_kotlin.dao.ComprobanteDao
import com.example.project_kotlin.dao.MetodoPagoDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.utils.appConfig
import com.example.project_kotlin.vistas.inicio.IndexComandasActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatosComprobantes : AppCompatActivity(){

    private lateinit var btnBuscar: Button
    private lateinit var btnVolver : Button

    private lateinit var spnCaja: Spinner
    private lateinit var edtFechaEmision: EditText
    private lateinit var edtPrecioInicial: EditText
    private lateinit var edtPrecioFin: EditText
    private lateinit var spnMetPago: Spinner
    private lateinit var tvEtiqueta : TextView
    private lateinit var listPagos:RecyclerView
    private lateinit var edtVentaTotal:EditText
    //BASE DE DATOS
    private lateinit var comprobanteDao : ComprobanteDao
    private lateinit var cajaDao : CajaDao
    private lateinit var metodoPagoDao : MetodoPagoDao
    private lateinit var adaptador : ComprobantesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.caja_activity)
        comprobanteDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).comprobanteDao()
        btnBuscar = findViewById(R.id.btnBuscarComprobantes)
        btnVolver = findViewById(R.id.btnVolverListadoComprobantes)
        spnCaja = findViewById(R.id.spnCajaComprobantes)
        edtFechaEmision = findViewById(R.id.edtFechaEmisionComprobantes)
        edtPrecioInicial = findViewById(R.id.edtPrecioInicialComprobantes)
        edtPrecioFin = findViewById(R.id.edtPrecioFinComprobantes)
        spnMetPago = findViewById(R.id.spnMetPagoComprobantes)
        listPagos = findViewById(R.id.rvComprobantes)
        edtVentaTotal = findViewById(R.id.edtVentaTotalComprobantes)
        tvEtiqueta = findViewById(R.id.tvDatosComprobantes)
        btnVolver.setOnClickListener{volver()}
        cajaDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).cajaDao()
        metodoPagoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).metodoPagoDao()
        btnBuscar.setOnClickListener{filtrar()}
        cargarCajas()
        cargarMetodosPago()
        cargarDatos()

    }
    fun filtrar(){
        
    }
    fun cargarDatos(){
        lifecycleScope.launch(Dispatchers.IO){
            var datos = comprobanteDao.obtenerTodo()
            if(datos.size != 0)
                tvEtiqueta.visibility = View.GONE
            withContext(Dispatchers.Main) {
                adaptador = ComprobantesAdapter(datos)
                listPagos.layoutManager= LinearLayoutManager(this@DatosComprobantes)
                listPagos.adapter = adaptador
                val sumaPrecio = datos.sumOf { it.comprobante.comprobante.comprobante.comprobante.comprobante.precioTotalPedido}
                edtVentaTotal.setText(sumaPrecio.toString())
            }


        }
    }
    fun cargarCajas(){
        lifecycleScope.launch(Dispatchers.IO){
            var cajas = cajaDao.obtenerTodo()
            val categorias = cajas.map { it.id }
            val adapter = ArrayAdapter(this@DatosComprobantes, android.R.layout.simple_spinner_item, categorias)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnCaja.adapter = adapter

        }
    }
    fun cargarMetodosPago(){
        lifecycleScope.launch(Dispatchers.IO){
            var metodos = metodoPagoDao.buscarTodo()
            val categorias = metodos.map { it.nombreMetodoPago }
            val adapter = ArrayAdapter(this@DatosComprobantes, android.R.layout.simple_spinner_item, categorias)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnMetPago.adapter = adapter
        }
    }
    fun volver() {
        val intent = Intent(this, IndexComandasActivity::class.java)
        startActivity(intent)
    }

}