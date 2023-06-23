package com.example.project_kotlin.vistas.comandas

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.vistas.inicio.IndexComandasActivity

class EditarComanda : AppCompatActivity() {
    private lateinit var edtNumMesa : EditText
    private lateinit var edtComensal : EditText
    private lateinit var edtEstadoComanda : EditText
    private lateinit var edtPrecioTotal : EditText
    private lateinit var edtEmpleado : EditText
    private lateinit var btnAniadirPlato : Button
    private lateinit var btnRegresarC : Button
    private lateinit var rvDetalleComanda : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actualizar_comanda_form)


        val btnAniadirPlato : Button = findViewById(R.id.btnAniadirPlato)

        edtNumMesa = findViewById(R.id.spnMesasActualizarComanda)
        edtComensal = findViewById(R.id.edtComensal)
        edtEstadoComanda = findViewById(R.id.edtEstadoComanda)
        edtPrecioTotal = findViewById(R.id.edtPrecioTotal)
        edtEmpleado = findViewById(R.id.edtEmpleado)
        btnRegresarC = findViewById(R.id.btnRegresarC)
        rvDetalleComanda = findViewById(R.id.rvDetalleComanda)
        btnRegresarC.setOnClickListener({volver()})

        btnAniadirPlato.setOnClickListener{
            val message : String? ="Agregar plato"
            dialogAgregarPlato(message)
        }

        btnRegresarC.setOnClickListener({volver()})

    }


    //dialog Aniadir
    private fun dialogAgregarPlato(message: String?){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_agregarplato_c)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMensaje : TextView =  dialog.findViewById(R.id.tvMensajeDialog)
        val spnCategoriaPlatoC : Spinner = dialog.findViewById(R.id.spnCategoriaPlatoC)
        val spnPlatoC : Spinner = dialog.findViewById(R.id.spnPlatoC)
        val edtCantidadPlatoC : EditText = dialog.findViewById(R.id.edtCantidadPlatoC)
        val edtObservacionPlatoD : EditText = dialog.findViewById(R.id.edtCantidadPlatoC)
        val btnAgregarPDetalle : Button = dialog.findViewById(R.id.btnRegistrarPlatoD)
        val btnCancelacionPDetalle : Button = dialog.findViewById(R.id.btnCancelarPlatoD)

        tvMensaje.text = message

        btnAgregarPDetalle.setOnClickListener{
            Toast.makeText(this,"Plato agregado", Toast.LENGTH_LONG).show()
        }

        btnCancelacionPDetalle.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }


    fun volver(){
        var intent = Intent(this, ComandasVista::class.java)
        startActivity(intent)
    }






}