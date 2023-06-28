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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.adaptador.adaptadores.comandas.DetalleComandaAdapter
import com.example.project_kotlin.dao.*
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.*
import com.example.project_kotlin.service.ApiServiceComanda
import com.example.project_kotlin.utils.VariablesGlobales
import com.example.project_kotlin.utils.appConfig
import com.example.project_kotlin.vistas.facturar.FacturarActivity
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditarComanda : AppCompatActivity(), DetalleComandaAdapter.OnItemClickLister {
    private lateinit var edtNumMesa: EditText
    private lateinit var edtComensal: EditText
    private lateinit var edtEstadoComanda: EditText
    private lateinit var edtPrecioTotal: EditText
    private lateinit var edtEmpleado: EditText
    private lateinit var rvDetalleComandaR : RecyclerView
    private lateinit var btnAniadirPlato: Button
    private lateinit var btnRegresarC: Button
    private lateinit var rvDetalleComanda: RecyclerView
    private lateinit var bdFirebase: DatabaseReference
    private lateinit var apiComanda: ApiServiceComanda
    private lateinit var spnCategoriaPlatoC:Spinner
    private lateinit var spnPlatoC:Spinner
    private lateinit var  btnAgregarPDetalle : Button
    private lateinit var btnFacturar:Button


    //Llamando al objeto comandas con mesa, empleados y estadocomanda
    private lateinit var comandabean: ComandaMesaYEmpleadoYEstadoComanda

    //BASE DE DATOS
    private lateinit var mesaDao: MesaDao
    private lateinit var comandaDao: ComandaDao
    private lateinit var categoriaDao: CategoriaPlatoDao
    private lateinit var platoDao: PlatoDao
    private lateinit var detalleDao: DetalleComandaDao

    //Entidades globales para guardar
    private lateinit var comandaGlobal: Comanda
    private lateinit var EmpleadoGlobal: EmpleadoUsuarioYCargo
    private var detalleComandaGlobal: MutableList<DetalleComandaConPlato> = mutableListOf()

    //ADAPTADOR
    private lateinit var adaptadorDetalle: DetalleComandaAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actualizar_comanda_form)
        val btnAniadirPlato: Button = findViewById(R.id.btnAniadirPlato)
        edtNumMesa = findViewById(R.id.edtNumeroMesa)
        edtComensal = findViewById(R.id.edtComensal)
        edtEstadoComanda = findViewById(R.id.edtEstadoComanda)
        edtPrecioTotal = findViewById(R.id.edtPrecioTotal)
        edtEmpleado = findViewById(R.id.edtEmpleado)
        btnRegresarC = findViewById(R.id.btnRegresarC)
        btnFacturar=findViewById(R.id.btnFacturar)
        rvDetalleComanda = findViewById(R.id.rvDetalleComanda)
        rvDetalleComandaR = findViewById(R.id.rvDetalleComanda)
        btnRegresarC.setOnClickListener({ volver() })

        //Cargar comanda con datos
        categoriaDao=ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).categoriaPlatoDao()
        comandaDao=ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).comandaDao()
        detalleDao=ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).detalleComandaDao()
        platoDao=ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).platoDao()
        comandabean = intent.getSerializableExtra("Comanda") as ComandaMesaYEmpleadoYEstadoComanda

        cargarDatos()

        btnAniadirPlato.setOnClickListener {
            val message: String? = "Agregar plato"
            dialogAgregarPlato(message)
        }
        btnFacturar.setOnClickListener({factuarIndex()})
        btnRegresarC.setOnClickListener({ volver() })

    }
    fun cargarDatos(){
        if (comandabean != null) {
            print("Entro aquí")
            if(VariablesGlobales.empleado != null){
                EmpleadoGlobal = VariablesGlobales.empleado!!
            }
            // Accede a los campos del objeto ComandaMesaYEmpleadoYEstadoComanda
            edtNumMesa.setText(comandabean.comanda.comanda.comanda.mesaId.toString())
            edtComensal.setText(comandabean.comanda.comanda.comanda.cantidadAsientos.toString())
            edtPrecioTotal.setText(comandabean.comanda.comanda.comanda.precioTotal.toString())
            edtEstadoComanda.setText(comandabean.estadoComanda.estadoComanda)
            edtEmpleado.setText(EmpleadoGlobal.empleado.empleado.nombreEmpleado + " " + EmpleadoGlobal.empleado.empleado.apellidoEmpleado)

            // Otros campos
        } else {
            print("No hay datos en comandas")
            // El objeto ComandaMesaYEmpleadoYEstadoComanda es nulo
        }


    }

    fun cargarCategorias(){
        lifecycleScope.launch(Dispatchers.IO){
            var categorias = categoriaDao.obtenerTodo()
            if(categorias.size > 0){
                val categorias = categorias.map { it.categoria }
                val adapter = ArrayAdapter(this@EditarComanda, android.R.layout.simple_spinner_item, categorias)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnCategoriaPlatoC.adapter = adapter
                cargarPlatosPorCategoria()

            } else {
                val opcion = "No hay categorias"
                val adapter = ArrayAdapter(this@EditarComanda, android.R.layout.simple_spinner_item, arrayOf(opcion)) // Crea un adaptador con la opción única
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
                val adapter = ArrayAdapter(this@EditarComanda, android.R.layout.simple_spinner_item, platos)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                runOnUiThread {
                    spnPlatoC.adapter = adapter
                }

            } else {
                val opcion = "No hay platos"
                val adapter = ArrayAdapter(this@EditarComanda, android.R.layout.simple_spinner_item, arrayOf(opcion)) // Crea un adaptador con la opción única
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Establece el layout para las opciones desplegables
                spnPlatoC.adapter = adapter
                runOnUiThread {
                    btnAgregarPDetalle.isEnabled = false
                }

            }

        }

    }



    //dialog Aniadir
    private fun dialogAgregarPlato(message: String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_agregarplato_c)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMensaje: TextView = dialog.findViewById(R.id.tvMensajeDialog)
        spnCategoriaPlatoC=dialog.findViewById(R.id.spnCategoriaPlatoC)
        spnPlatoC = dialog.findViewById(R.id.spnPlatoC)
        cargarCategorias()
        spnCategoriaPlatoC.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                cargarPlatosPorCategoria()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                btnAgregarPDetalle.isEnabled = false
            }
        })

        val edtCantidadPlatoC: EditText = dialog.findViewById(R.id.edtCantidadPlatoC)
        val edtObservacionPlatoD: EditText = dialog.findViewById(R.id.edtCantidadPlatoC)
        val btnAgregarPDetalle: Button = dialog.findViewById(R.id.btnRegistrarPlatoD)
        val btnCancelacionPDetalle: Button = dialog.findViewById(R.id.btnCancelarPlatoD)
        tvMensaje.text = message

        btnAgregarPDetalle.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                //Obtener el plato
                val cantidadInicialPlatos = detalleComandaGlobal.size
                val numero = (spnPlatoC.selectedItemPosition + 1)
                val idPlato = "P-${String.format("%03d", numero)}"
                val platoDato = platoDao.obtenerPorId(idPlato)

                val cantidadValidar = edtCantidadPlatoC.text.toString().toIntOrNull()
                if (cantidadValidar == null) {
                    mostrarToast("Ingrese una cantidad")
                    return@launch
                }
                //Obtener valores de los inputs
                val cantidadPedido = edtCantidadPlatoC.text.toString().toInt()
                val precioUnitario = platoDato.precioPlato * cantidadPedido
                var observacion = edtObservacionPlatoD.text.toString()
                val detalleExistente = detalleComandaGlobal.find { it.plato.id == platoDato.id }
                if (detalleExistente != null) {
                    val sumamatorio = detalleComandaGlobal.sumOf { it.detalle.precioUnitario }
                    detalleExistente.detalle.precioUnitario += sumamatorio
                    detalleExistente.detalle.observacion += observacion
                    detalleExistente.detalle.cantidadPedido += cantidadPedido
                } else {
                    val detalle = DetalleComanda(
                        comandaId = 0,
                        cantidadPedido = cantidadPedido,
                        precioUnitario = precioUnitario,
                        idPlato = idPlato,
                        observacion = observacion
                    )
                    val detalleAgregar = DetalleComandaConPlato(detalle, platoDato)
                    detalleComandaGlobal.add(detalleAgregar)
                }
                     withContext(Dispatchers.Main) {
                    if (cantidadInicialPlatos == 0) {
                        adaptadorDetalle = DetalleComandaAdapter(detalleComandaGlobal, this@EditarComanda)
                        rvDetalleComandaR.layoutManager = LinearLayoutManager(appConfig.CONTEXT)
                        rvDetalleComandaR.adapter = adaptadorDetalle
                    } else {
                        adaptadorDetalle.actualizarDetalleComanda(detalleComandaGlobal)
                    }
                    val sumaPrecio = detalleComandaGlobal.sumOf { it.detalle.precioUnitario }
                    edtPrecioTotal.setText(sumaPrecio.toString())
                    dialog.dismiss()

                }
            }

        }
            btnCancelacionPDetalle.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }

    fun volver(){
        var intent = Intent(this, ComandasVista::class.java)
        startActivity(intent)
    }

    fun factuarIndex(){
        var intentFacturar=Intent(this,FacturarActivity::class.java)
        intentFacturar.putExtra("comandaFacturar", comandabean)
        startActivity(intentFacturar)
    }
    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(appConfig.CONTEXT, mensaje, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemDeleteClick(detalle: DetalleComandaConPlato) {
        TODO("Not yet implemented")
    }

    override fun onItemUpdateClick(detalle: DetalleComandaConPlato) {
        TODO("Not yet implemented")
    }


}