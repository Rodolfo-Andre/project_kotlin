package com.example.project_kotlin.vistas.facturar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project_kotlin.R
import com.example.project_kotlin.entidades.Comanda
import com.example.project_kotlin.entidades.ComandaMesaYEmpleadoYEstadoComanda
import com.example.project_kotlin.entidades.Comprobante
import com.example.project_kotlin.entidades.ComprobanteComandaYEmpleadoYCajaYTipoComprobanteYMetodoPago


class DetallesComprobante : AppCompatActivity() {
    private lateinit var comprobantelobal: ComprobanteComandaYEmpleadoYCajaYTipoComprobanteYMetodoPago

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actualizar_comanda_form)
        comprobantelobal = intent.getSerializableExtra("comprobante") as ComprobanteComandaYEmpleadoYCajaYTipoComprobanteYMetodoPago


    }
}