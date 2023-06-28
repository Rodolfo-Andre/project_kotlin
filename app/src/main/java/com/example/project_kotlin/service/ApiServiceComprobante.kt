package com.example.project_kotlin.service

import com.example.project_kotlin.entidades.dto.ComprobanteDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceComprobante {
    @POST("/configuracion/comprobante/registrar")
    fun fetchGuardarEmpleado(@Body bean: ComprobanteDTO): Call<Void>

}