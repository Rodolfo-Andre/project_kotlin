package com.example.project_kotlin.vistas.comandas

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.dao.CategoriaPlatoDao
import com.example.project_kotlin.dao.ComandaDao
import com.example.project_kotlin.dao.MesaDao
import com.example.project_kotlin.dao.PlatoDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.Comanda
import com.example.project_kotlin.entidades.DetalleComanda
import com.example.project_kotlin.entidades.DetalleComandaConPlato
import com.example.project_kotlin.service.ApiServiceComanda
import com.example.project_kotlin.utils.appConfig
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrarComanda : AppCompatActivity() {
    private lateinit var  btnAgregarPDetalle : Button
    private lateinit var spnMesas : Spinner
    private lateinit var spnCategoriaPlatoC : Spinner
    private lateinit var spnPlatoC : Spinner
    private lateinit var edtComensalR : EditText
    private lateinit var btnGenerarComanda : Button
    private lateinit var edtEstadoComandaR : EditText
    private lateinit var edtPrecioTotalR : EditText
    private lateinit var edtEmpleadoR : EditText
    private lateinit var rvDetalleComandaR : RecyclerView
    private lateinit var btnAniadirPlato : Button
    private lateinit var btnRegresarCR : Button
    private lateinit var bdFirebase : DatabaseReference
    private lateinit var apiComanda : ApiServiceComanda
    //BASE DE DATOS
    private lateinit var mesaDao : MesaDao
    private lateinit var comandaDao : ComandaDao
    private lateinit var categoriaDao : CategoriaPlatoDao
    private lateinit var platoDao : PlatoDao
    //Entidades globales para guardar
    private lateinit var comandaGlobal : Comanda
    private  var detalleComandaGlobal : List<DetalleComandaConPlato> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrar_comanda_form)

        //validaciones de roles
        btnGenerarComanda = findViewById(R.id.btnGenerarComandaR)
        btnRegresarCR = findViewById(R.id.btnRegresarCR)
        btnAniadirPlato = findViewById(R.id.btnAniadirPlatoR)
        spnMesas = findViewById(R.id.spnMesasRComanda)
        edtComensalR = findViewById(R.id.edtComensalR)
        edtEstadoComandaR = findViewById(R.id.edtEstadoComandaR)
        edtPrecioTotalR = findViewById(R.id.edtPrecioTotalR)
        edtEmpleadoR = findViewById(R.id.edtEmpleadoR)
        mesaDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).mesaDao()
        categoriaDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).categoriaPlatoDao()
        platoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).platoDao()
        comandaDao =  ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).comandaDao()
        rvDetalleComandaR = findViewById(R.id.rvDetalleComanda)
        btnAniadirPlato.setOnClickListener{
            val message : String? ="Agregar plato"
            dialogAgregarPlato(message)
        }
        btnRegresarCR.setOnClickListener({volver()})


        cargarMesasLibres()

        conectar()
    }
    fun volver(){
        var intent = Intent(this, ComandasVista::class.java)
        startActivity(intent)
    }

    fun conectar(){
        //Iniciar firebase en la clase actual
        FirebaseApp.initializeApp(this)
        bdFirebase = FirebaseDatabase.getInstance().reference
    }

    //dialog Aniadir
    private fun dialogAgregarPlato(message: String?){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_agregarplato_c)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMensaje : TextView =  dialog.findViewById(R.id.tvMensajeDialog)
        spnCategoriaPlatoC  = dialog.findViewById(R.id.spnCategoriaPlatoC)
        spnPlatoC = dialog.findViewById(R.id.spnPlatoC)
        //Agregar categorías
        cargarCategoriasPlatos()
        spnCategoriaPlatoC.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                cargarPlatosPorCategoria()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                btnAgregarPDetalle.isEnabled = false
            }
        })
        val edtCantidadPlatoC : EditText = dialog.findViewById(R.id.edtCantidadPlatoC)
        val edtObservacionPlatoD : EditText = dialog.findViewById(R.id.edtCantidadPlatoC)
        btnAgregarPDetalle  = dialog.findViewById(R.id.btnRegistrarPlatoD)
        val btnCancelacionPDetalle : Button = dialog.findViewById(R.id.btnCancelarPlatoD)

        tvMensaje.text = message

        btnAgregarPDetalle.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO){
                //Obtener el plato
                val numero = (spnPlatoC.selectedItemPosition+1)
                val idPlato = "P-${String.format("%03d", numero)}"
                val platoDato = platoDao.obtenerPorId(idPlato)

                val cantidadValidar = edtCantidadPlatoC.text.toString().toIntOrNull()
                if(cantidadValidar == null) {
                    mostrarToast("Ingrese una cantidad")
                    return@launch
                }
                //Obtener valores de los inputs
                val cantidadPedido = edtCantidadPlatoC.text.toString().toInt()
                val precioUnitario = platoDato.precioPlato * cantidadPedido
                var observacion = edtObservacionPlatoD.text.toString()
                val detalle = DetalleComanda(comandaId = 0, cantidadPedido = cantidadPedido, precioUnitario = precioUnitario, idPlato = idPlato, observacion = observacion)
                val detalleAgregar = DetalleComandaConPlato(detalle, platoDato)
                detalleComandaGlobal = detalleComandaGlobal + detalleAgregar
                mostrarToast("Plato agregado")

            }

        }

        btnCancelacionPDetalle.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }
    fun cargarCategoriasPlatos(){
        lifecycleScope.launch(Dispatchers.IO){
            var categorias = categoriaDao.obtenerTodo()
            if(categorias.size > 0){
                val categorias = categorias.map { it.categoria }
                val adapter = ArrayAdapter(this@RegistrarComanda, android.R.layout.simple_spinner_item, categorias)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnCategoriaPlatoC.adapter = adapter
                cargarPlatosPorCategoria()

            } else {
                val opcion = "No hay categorias"
                val adapter = ArrayAdapter(this@RegistrarComanda, android.R.layout.simple_spinner_item, arrayOf(opcion)) // Crea un adaptador con la opción única
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Establece el layout para las opciones desplegables
                spnCategoriaPlatoC.adapter = adapter
                runOnUiThread {
                    btnAgregarPDetalle.isEnabled = false
                }

            }

        }
    }
    private fun cargarPlatosPorCategoria(){
        lifecycleScope.launch(Dispatchers.IO){

            var numero = (spnCategoriaPlatoC.selectedItemPosition+1)
            var idCatPlato = "C-${String.format("%03d", numero)}"
            var platos = platoDao.obtenerPlatosPorCategoria(idCatPlato)

            if(platos.size > 0){
                val platos = platos.map { it.plato.nombrePlato }
                val adapter = ArrayAdapter(this@RegistrarComanda, android.R.layout.simple_spinner_item, platos)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                runOnUiThread {
                spnPlatoC.adapter = adapter
                }

            } else {
                val opcion = "No hay platos"
                val adapter = ArrayAdapter(this@RegistrarComanda, android.R.layout.simple_spinner_item, arrayOf(opcion)) // Crea un adaptador con la opción única
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Establece el layout para las opciones desplegables
                spnPlatoC.adapter = adapter
                runOnUiThread {
                    btnAgregarPDetalle.isEnabled = false
                }

            }

        }

    }
    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(appConfig.CONTEXT, mensaje, Toast.LENGTH_SHORT).show()
        }
    }

    fun cargarMesasLibres(){
        lifecycleScope.launch(Dispatchers.IO){
            var mesas = mesaDao.obtenerMesasLibres()
            if(mesas.size > 0){
                val mesasID = mesas.map { it.id }
                val adapter = ArrayAdapter(this@RegistrarComanda, android.R.layout.simple_spinner_item, mesasID)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnMesas.adapter = adapter

            }else {
                val opcion = "No hay mesas libres"
                val adapter = ArrayAdapter(this@RegistrarComanda, android.R.layout.simple_spinner_item, arrayOf(opcion)) // Crea un adaptador con la opción única
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Establece el layout para las opciones desplegables
                spnMesas.adapter = adapter

                mostrarToast("No hay mesas libres")
                runOnUiThread {
                    btnGenerarComanda.isEnabled = false
                    btnAniadirPlato.isEnabled = false
                }

            }

        }
    }






}