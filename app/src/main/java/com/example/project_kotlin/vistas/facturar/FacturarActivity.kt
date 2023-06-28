package com.example.project_kotlin.vistas.facturar

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.R
import com.example.project_kotlin.dao.*
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.ComandaMesaYEmpleadoYEstadoComanda
import com.example.project_kotlin.entidades.DetalleComanda
import com.example.project_kotlin.entidades.DetalleComandaConPlato
import com.example.project_kotlin.service.ApiServiceComanda
import com.example.project_kotlin.service.ApiServiceComprobante
import com.example.project_kotlin.service.ApiServiceMesa
import com.example.project_kotlin.utils.ApiUtils
import com.example.project_kotlin.utils.VariablesGlobales
import com.example.project_kotlin.utils.appConfig
import com.example.project_kotlin.vistas.comandas.EditarComanda
import com.example.project_kotlin.vistas.mesas.DatosMesas
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FacturarActivity: AppCompatActivity() {
    //BOTONES
    private lateinit var btnFacturar : Button
    private lateinit var btnVolver : Button
    //Inputs
    private lateinit var spnMetodoPago : Spinner
    private lateinit var spnTipoComprobante : Spinner
    private lateinit var spnCaja : Spinner
    private lateinit var edIdComanda : EditText
    private lateinit var edEmpleado : EditText
    private lateinit var edCliente : EditText
    private lateinit var edIGV : EditText
    private lateinit var edSubTotal : EditText
    private lateinit var edDescuento : EditText
    private lateinit var edTotalPagar : EditText
    //BD
    private lateinit var detalleComandaDao : DetalleComandaDao
    private lateinit var comprobanteDao : ComprobanteDao
    private lateinit var apiComprobante : ApiServiceComprobante
    private lateinit var mesaDao: MesaDao
    private lateinit var apiMesa : ApiServiceMesa
    private lateinit var comandaDao: ComandaDao
    private lateinit var apiComanda : ApiServiceComanda
    private lateinit var cajaDao : CajaDao
    private lateinit var metodoPagoDao : MetodoPagoDao
    private lateinit var tipoComprobanteDao : TipoComprobanteDao
    private lateinit var bdFirebase : DatabaseReference
    //ComandaGlobal
    //Llamando al objeto comandas con mesa, empleados y estadocomanda
    private lateinit var comandabean: ComandaMesaYEmpleadoYEstadoComanda
    private  var detalleComandaGlobal : List<DetalleComandaConPlato> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.facturar_activity)
        //Inicializar componentes
        btnFacturar = findViewById(R.id.BtnFacturar)
        btnVolver = findViewById(R.id.BtnCancelar)
        spnMetodoPago = findViewById(R.id.spnMetPagoF)
        spnTipoComprobante = findViewById(R.id.spnTipoComprobante)
        spnCaja = findViewById(R.id.spnCajasP)
        edIdComanda = findViewById(R.id.edtIdComanda)
        edEmpleado = findViewById(R.id.edtEmpleadoNombreF)
        edCliente = findViewById(R.id.edtClienteF)
        edIGV = findViewById(R.id.edtIGV)
        edSubTotal = findViewById(R.id.edtSubTotal)
        edDescuento = findViewById(R.id.edtSubTotal)
        edTotalPagar = findViewById(R.id.edtPrecioTotal)
        //Inicializar room
        comprobanteDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).comprobanteDao()
        mesaDao =ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).mesaDao()
        apiMesa = ApiUtils.getAPIServiceMesa()
        comandaDao=ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).comandaDao()
        apiComanda= ApiUtils.getApiServiceComanda()
        cajaDao =ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).cajaDao()
        metodoPagoDao =ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).metodoPagoDao()
        tipoComprobanteDao =ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).tipoComprobanteDao()
        detalleComandaDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).detalleComandaDao()
        apiComprobante = ApiUtils.getApiServiceComprobante()
        conectar()
        //Comanda global
        comandabean = intent.getSerializableExtra("comandaFacturar") as ComandaMesaYEmpleadoYEstadoComanda
        lifecycleScope.launch(Dispatchers.IO) {
            detalleComandaGlobal = detalleComandaDao.buscarDetallesPorComanda(comandabean.comanda.comanda.comanda.id)
        }
        //EVENTOS
        btnVolver.setOnClickListener{volver()}
        btnFacturar.setOnClickListener{facturar()}
        cargarDatos()

    }
    private fun cargarDatos(){
        //DATOS
        edIdComanda.setText(comandabean.comanda.comanda.comanda.id.toString())
        edEmpleado.setText(VariablesGlobales.empleado?.empleado?.empleado?.nombreEmpleado)
        //CALCULAR
        val sumaPrecio = detalleComandaGlobal.sumOf { it.detalle.precioUnitario }
        edSubTotal.setText(sumaPrecio.toString())
        val igv = sumaPrecio * 0.18
        edIGV.setText(igv.toString())
        val totalPagar = sumaPrecio + igv
        edTotalPagar.setText(totalPagar.toString())
        cargarCajas()
        cargarMetodosPago()
        cargarTiposComprobante()
    }
    fun cargarMetodosPago(){
        lifecycleScope.launch(Dispatchers.IO){
            var metodos = metodoPagoDao.buscarTodo()
            if(metodos.size > 0){
                val categorias = metodos.map { it.nombreMetodoPago }
                val adapter = ArrayAdapter(this@FacturarActivity, android.R.layout.simple_spinner_item, categorias)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnMetodoPago.adapter = adapter
            } else {
                val opcion = "No hay métodos de pago"
                val adapter = ArrayAdapter(this@FacturarActivity, android.R.layout.simple_spinner_item, arrayOf(opcion)) // Crea un adaptador con la opción única
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Establece el layout para las opciones desplegables
                spnMetodoPago.adapter = adapter
                runOnUiThread {
                    btnFacturar.isEnabled = false
                }

            }
        }
    }
    fun cargarCajas(){
        lifecycleScope.launch(Dispatchers.IO){
            var cajas = cajaDao.obtenerTodo()
            if(cajas.size > 0){
                val categorias = cajas.map { it.id }
                val adapter = ArrayAdapter(this@FacturarActivity, android.R.layout.simple_spinner_item, categorias)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnCaja.adapter = adapter
            } else {
                val opcion = "No hay cajas"
                val adapter = ArrayAdapter(this@FacturarActivity, android.R.layout.simple_spinner_item, arrayOf(opcion)) // Crea un adaptador con la opción única
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Establece el layout para las opciones desplegables
                spnCaja.adapter = adapter
                runOnUiThread {
                    btnFacturar.isEnabled = false
                }

            }
        }
    }
    fun cargarTiposComprobante(){
        lifecycleScope.launch(Dispatchers.IO){
            var tiposComprobante = tipoComprobanteDao.obtenerTodo()
            val categorias = tiposComprobante.map { it.tipo }
            val adapter = ArrayAdapter(this@FacturarActivity, android.R.layout.simple_spinner_item, categorias)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnTipoComprobante.adapter = adapter
        }
    }
    fun facturar(){

    }
    fun volver() {
        val intent = Intent(this, EditarComanda::class.java)
        intent.putExtra("Comanda", comandabean)
        startActivity(intent)
    }
    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(appConfig.CONTEXT, mensaje, Toast.LENGTH_SHORT).show()
        }
    }

    fun conectar(){
        //Iniciar firebase en la clase actual
        FirebaseApp.initializeApp(this)
        bdFirebase = FirebaseDatabase.getInstance().reference
    }
}