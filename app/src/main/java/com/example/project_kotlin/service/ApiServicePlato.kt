package com.example.project_kotlin.service

import androidx.room.Delete
import com.example.project_kotlin.entidades.Plato
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServicePlato {

    @POST("/configuracion/plato/registrar")
    fun fetchGuardarPlato(@Body bean:Plato): Call<Void>

    @PUT("/configuracion/plato/actualizar")
    fun fectchActualizarPlato(@Body bean:Plato): Call<Void>

     @DELETE("/configuracion/plato/eliminar/{codigo}")
     fun fetchEliminarPlato(@Path("codigo") codigo:String): Call<Void>

}