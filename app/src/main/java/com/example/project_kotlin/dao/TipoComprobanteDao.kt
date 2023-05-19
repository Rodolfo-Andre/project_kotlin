package com.example.project_kotlin.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.project_kotlin.entidades.TipoComprobante

@Dao
interface TipoComprobanteDao {

    @Query("select * from Tipo_Comprobante")
    fun obtenerTodo() : List<TipoComprobante>
}