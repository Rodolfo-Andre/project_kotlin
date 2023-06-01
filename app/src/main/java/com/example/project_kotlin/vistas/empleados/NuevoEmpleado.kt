package com.example.project_kotlin.vistas.empleados

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.R
import com.example.project_kotlin.dao.CargoDao
import com.example.project_kotlin.dao.EmpleadoDao
import com.example.project_kotlin.dao.UsuarioDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.Cargo
import com.example.project_kotlin.entidades.Empleado
import com.example.project_kotlin.entidades.Usuario
import com.example.project_kotlin.utils.appConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NuevoEmpleado:AppCompatActivity() {

    private lateinit var edtNomUsu:EditText
    private lateinit var edtApeUsu:EditText
    private lateinit var edtDniUsu:EditText
    private lateinit var edtCorreoUsu:EditText
    private lateinit var edtTelfUsu:EditText
    private lateinit var spnCargo:Spinner
    private lateinit var btnNuevoUsu:Button
    private lateinit var btnCancelarUsu:Button
    //BASE DE DATOS
    private lateinit var cargoDao : CargoDao
    private lateinit var empleadoDao : EmpleadoDao
    private lateinit var usuarioDao : UsuarioDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agregar_usu)

        edtNomUsu = findViewById(R.id.edtNomUsuE)
        edtApeUsu = findViewById(R.id.edtApeUsuE)
        edtDniUsu = findViewById(R.id.edtDniUsuE)
        edtCorreoUsu = findViewById(R.id.edtCorreoUsuE)
        edtTelfUsu = findViewById(R.id.edtTelfUsuE)
        spnCargo = findViewById(R.id.spnCargoEmpleadoE)
        btnNuevoUsu = findViewById(R.id.btnNuevoUsu)
        btnCancelarUsu = findViewById(R.id.btnEliminarUsu)
        //Base de datos
        cargoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).cargoDao()
        empleadoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).empleadoDao()
        usuarioDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).usuarioDao()
        cargarCargos()
        btnNuevoUsu.setOnClickListener({nuevoUsuario()})
        btnCancelarUsu.setOnClickListener({volver()})



    }
    fun nuevoUsuario(){
        lifecycleScope.launch(Dispatchers.IO) {
            if(validarCampos()){
                val nombre = edtNomUsu.text.toString()
                val apellido = edtApeUsu.text.toString()
                val dni = edtDniUsu.text.toString()
                val correo = edtCorreoUsu.text.toString()
                val tel = edtTelfUsu.text.toString()
                val cargo = spnCargo.selectedItemPosition +1

                //Crear objeto usuario
                val usuario = Usuario(correo = correo)
                usuario.contrasena = usuario.generarContrasenia(apellido)
                val idUsuario = usuarioDao.guardar(usuario)
                usuario.id = idUsuario
                val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                val fechaActual = Date()
                val fechaFormateada = dateFormat.format(fechaActual)
                val empleado = Empleado(nombreEmpleado = nombre, apellidoEmpleado = apellido, dniEmpleado = dni,
                    telefonoEmpleado = tel, fechaRegistro = fechaFormateada)
                empleado.usuario = usuario
                empleado.cargo = Cargo(id = cargo.toLong(), cargo = spnCargo.selectedItem.toString())
                empleadoDao.guardar(empleado)
                mostrarToast("Empleado guardado correctamente")
                volver()

            }
        }
    }
    fun validarCampos() : Boolean{
        val nombre = edtNomUsu.text.toString()
        val apellido = edtApeUsu.text.toString()
        val dni = edtDniUsu.text.toString()
        val correo = edtCorreoUsu.text.toString()
        val tel = edtTelfUsu.text.toString()
        val REGEX_NOMBRE = "^(?=.{3,40}\$)[A-ZÑÁÉÍÓÚ][a-zñáéíóú]+(?: [A-ZÑÁÉÍÓÚ][a-zñáéíóú]+)*\$"
        val REGEX_APELLIDO = "^(?=.{3,40}\$)[A-ZÑÁÉÍÓÚ][a-zñáéíóú]+(?: [A-ZÑÁÉÍÓÚ][a-zñáéíóú]+)*\$"
        val REGEX_CORREO = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        val REGEX_TELEFONO = "^9[0-9]{8}\$"
        val REGEX_DNI = "^[0-9]{8}\$"

        if (!REGEX_NOMBRE.toRegex().matches(nombre)) {
            // El campo nombre no cumple con el formato esperado
            mostrarToast("El campo nombre no cumple con el formato requerido. Debe comenzar con mayúscula y contener solo letras y espacios")
            return false
        }
        if (!REGEX_APELLIDO.toRegex().matches(apellido)) {
            // El campo nombre no cumple con el formato esperado
            mostrarToast("El campo apellido no cumple con el formato requerido. Debe comenzar con mayúscula y contener solo letras y espacios")
            return false
        }
        if (!REGEX_DNI.toRegex().matches(dni)) {
            // El campo nombre no cumple con el formato esperado
            mostrarToast("Ingresa un DNI valido")
            return false
        }
        if (!REGEX_CORREO.toRegex().matches(correo)) {
            // El campo nombre no cumple con el formato esperado
            mostrarToast("Ingresa un correo valido")
            return false
        }
        if (!REGEX_TELEFONO.toRegex().matches(tel)) {
            // El campo nombre no cumple con el formato esperado
            mostrarToast("Ingresa un teléfono válido, debe empezar con 9 y contar con 9 dígitos")
            return false
        }

        return true
    }
    fun volver(){
        var intent = Intent(this, DatosEmpleados::class.java)
        startActivity(intent)
    }
    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(appConfig.CONTEXT, mensaje, Toast.LENGTH_SHORT).show()
        }
    }
    fun cargarCargos(){
        lifecycleScope.launch(Dispatchers.IO){
            val cargos = cargoDao.obtenerTodo()
            val nombresCargos = cargos.map { it.cargo }
            val adapter = ArrayAdapter(this@NuevoEmpleado, android.R.layout.simple_spinner_item, nombresCargos)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnCargo.adapter = adapter

        }
    }


}