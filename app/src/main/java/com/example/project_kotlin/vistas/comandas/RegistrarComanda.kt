package com.example.project_kotlin.vistas.comandas

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R

class RegistrarComanda : AppCompatActivity() {
    private lateinit var edtNumMesaR : EditText
    private lateinit var edtComensalR : EditText
    private lateinit var edtEstadoComandaR : EditText
    private lateinit var edtPrecioTotalR : EditText
    private lateinit var edtEmpleadoR : EditText
    private lateinit var btnCancelarDetalleCR : Button
    //private lateinit var btnAniadirPlatoR : Button
    //private lateinit var btnRegresarCR : Button
    private lateinit var rvDetalleComandaR : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrar_comanda_form)

        val btnRegresarCR : Button = findViewById(R.id.btnRegresarCR)
        val btnAniadirPlato : Button = findViewById(R.id.btnAniadirPlatoR)

        edtNumMesaR = findViewById(R.id.edtIdComanda)
        edtComensalR = findViewById(R.id.edtComensalR)
        edtEstadoComandaR = findViewById(R.id.edtEstadoComandaR)
        edtPrecioTotalR = findViewById(R.id.edtPrecioTotalR)
        edtEmpleadoR = findViewById(R.id.edtEmpleadoR)
        //btnAniadirPlatoR = findViewById(R.id.btnAniadirPlatoR)
        //btnRegresarCR = findViewById(R.id.btnRegresarCR)
        rvDetalleComandaR = findViewById(R.id.rvDetalleComanda)


        //btnAniadirPlatoR.setOnClickListener {agregarPlatoVista()}




        btnAniadirPlato.setOnClickListener{
            val message : String? ="Agregar plato"
            dialogAgregarPlato(message)
        }

        btnRegresarCR.setOnClickListener{
            val message : String? ="¿Se eliminara el plato de la comanda?"
            dialogConfirmacion(message)
        }

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
            Toast.makeText(this,"Plato agregado",Toast.LENGTH_LONG).show()
        }

        btnCancelacionPDetalle.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }


    //dialog eliminar
    private fun dialogConfirmacion(message: String?){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_eliminarplato_c)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMensaje : TextView =  dialog.findViewById(R.id.tvMensajeDialog)
        val btnConfirmacion : Button = dialog.findViewById(R.id.btnEliminarPlatoC)
        val btnCancelacion : Button = dialog.findViewById(R.id.btnCancelarEliminarPC)

        tvMensaje.text = message

        btnConfirmacion.setOnClickListener{
            Toast.makeText(this,"confirmación de boton",Toast.LENGTH_LONG).show()
        }

        btnCancelacion.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }






}