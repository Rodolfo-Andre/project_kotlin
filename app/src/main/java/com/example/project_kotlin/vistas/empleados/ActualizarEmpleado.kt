package com.example.project_kotlin.vistas.empleados

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.R
import com.example.project_kotlin.dao.CargoDao
import com.example.project_kotlin.dao.EmpleadoDao
import com.example.project_kotlin.dao.UsuarioDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.*
import com.example.project_kotlin.entidades.dto.EmpleadoDTO
import com.example.project_kotlin.service.ApiServiceEmpleado
import com.example.project_kotlin.utils.ApiUtils
import com.example.project_kotlin.utils.appConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActualizarEmpleado : AppCompatActivity() {
    private lateinit var edtNomUsu: EditText
    private lateinit var edtApeUsu: EditText
    private lateinit var edtDniUsu: EditText
    private lateinit var edtCorreoUsu: EditText
    private lateinit var edtTelfUsu: EditText
    private lateinit var spnCargo: Spinner
    private lateinit var btnActualizarUsu: Button
    private lateinit var btnCancelarUsu: Button
    private lateinit var btnEliminarUsu: Button
    //BASE DE DATOS
    private lateinit var cargoDao : CargoDao
    private lateinit var empleadoDao : EmpleadoDao
    private lateinit var usuarioDao : UsuarioDao
    private lateinit var empleadoBean : EmpleadoUsuarioYCargo
    private lateinit var apiEmpleado : ApiServiceEmpleado
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actualizar_usu)
        apiEmpleado = ApiUtils.getAPIServiceEmpleado()
        edtNomUsu = findViewById(R.id.edtNomUsuA)
        edtApeUsu = findViewById(R.id.edtApeUsuA)
        edtDniUsu = findViewById(R.id.edtDniUsuA)
        edtCorreoUsu = findViewById(R.id.edtCorreoUsuA)
        edtTelfUsu = findViewById(R.id.edtTelfUsuA)
        spnCargo = findViewById(R.id.spnCargoEmpleadoA)
        btnActualizarUsu = findViewById(R.id.btnActualizarUsu)
        btnCancelarUsu = findViewById(R.id.btnVolverEditarUsu)
        btnEliminarUsu = findViewById(R.id.btnEliminarUsu)
        //Base de datos
        cargoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).cargoDao()
        empleadoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).empleadoDao()
        usuarioDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).usuarioDao()
        empleadoBean = intent.getSerializableExtra("empleado") as EmpleadoUsuarioYCargo
        cargarCargos()
        cargarDatos()
        btnActualizarUsu.setOnClickListener({actualizarUsu()})
        btnCancelarUsu.setOnClickListener({volver()})
        btnEliminarUsu.setOnClickListener({eliminar()})



    }
    fun cargarDatos(){
        edtNomUsu.setText(empleadoBean.empleado.empleado.nombreEmpleado)
        edtApeUsu.setText(empleadoBean.empleado.empleado.apellidoEmpleado)
        edtDniUsu.setText(empleadoBean.empleado.empleado.dniEmpleado)
        edtCorreoUsu.setText(empleadoBean.usuario.correo)
        edtTelfUsu.setText(empleadoBean.empleado.empleado.telefonoEmpleado)
        spnCargo.setSelection(empleadoBean.empleado.cargo.id.toInt()-1)

    }
    fun eliminar(){
        //VALIDACIÓ NDE NO PODER ELIMINAR EL USUARIO EN SESIÓN FALTA
        val mensaje: AlertDialog.Builder = AlertDialog.Builder(this)
        mensaje.setTitle("Sistema comandas")
        mensaje.setMessage("¿Seguro de eliminar?")
        mensaje.setCancelable(false)
        mensaje.setPositiveButton("Aceptar") { _, _ ->
            lifecycleScope.launch(Dispatchers.IO) {
                empleadoDao.eliminar(empleadoBean.empleado.empleado)
                usuarioDao.eliminar(empleadoBean.usuario)
                eliminarEmpleadoMysql(empleadoBean.empleado.empleado.id)
                mostrarToast("Empleado eliminado")
                volver()

            }
        }
        mensaje.setNegativeButton("Cancelar") { _, _ -> }
        mensaje.setIcon(android.R.drawable.ic_delete)
        mensaje.show()
    }
    fun eliminarEmpleadoMysql(id:Long){
        apiEmpleado.fetcEliminarEmpleado(id.toInt()).enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Error : ",t.toString())
            }
        })
    }
    fun actualizarUsu(){
        lifecycleScope.launch(Dispatchers.IO) {
            if(validarCampos()){
                val nombre = edtNomUsu.text.toString()
                val apellido = edtApeUsu.text.toString()
                val dni = edtDniUsu.text.toString()
                val correo = edtCorreoUsu.text.toString()
                val tel = edtTelfUsu.text.toString()
                val cargo = spnCargo.selectedItemPosition +1
                //AQUÍ TENDRÍA QUE VALIDAR QUE EL USUARIO NO PUEDA MODIFICAR SU CARGO
                val empleados = empleadoDao.obtenerTodo()
                val dniRepetido = empleados.any{it.empleado.empleado.dniEmpleado == dni && it.empleado.empleado.id != empleadoBean.empleado.empleado.id}
                val correoRepetido = empleados.any{it.usuario.correo == correo && it.empleado.empleado.id != empleadoBean.empleado.empleado.id}
                if(dniRepetido){
                    mostrarToast("El DNI ya existe en otro empleado")
                    return@launch
                }
                if(correoRepetido){
                    mostrarToast("El correo ya existe en otro empleado")
                    return@launch
                }
                empleadoBean.empleado.empleado.nombreEmpleado = nombre
                empleadoBean.empleado.empleado.apellidoEmpleado = apellido
                empleadoBean.empleado.empleado.dniEmpleado = dni
                empleadoBean.usuario.correo = correo
                empleadoBean.empleado.empleado.telefonoEmpleado = tel
                empleadoBean.empleado.cargo.id = cargo.toLong()
                empleadoBean.empleado.cargo.cargo = spnCargo.selectedItem.toString()
                usuarioDao.actualizar(empleadoBean.usuario)
                empleadoDao.actualizar(empleadoBean.empleado.empleado)

                val empleadoDTO = EmpleadoDTO(empleadoBean.empleado.empleado.id, nombre, apellido, tel, dni, empleadoBean.empleado.empleado.fechaRegistro, empleadoBean.usuario, empleadoBean.empleado.cargo)
                Log.e("Error al actualizar: ","" +empleadoDTO)
                actualizarEmpleadoMysql(empleadoDTO)
                mostrarToast("Empleado actualizado correctamente")
                volver()

            }
        }
    }
    fun actualizarEmpleadoMysql(bean: EmpleadoDTO){
        apiEmpleado.fetchActualizarEmpleado(bean).enqueue(object:Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Error al actualizar: ",t.toString())
            }
        })
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
            val adapter = ArrayAdapter(this@ActualizarEmpleado, android.R.layout.simple_spinner_item, nombresCargos)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnCargo.adapter = adapter

        }
    }
}