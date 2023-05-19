package com.example.project_kotlin.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.project_kotlin.entidades.Comprobante
import com.example.project_kotlin.entidades.Establecimiento

@Dao
interface EstablecimientoDao {
    @Query("select * from Establecimiento")
    fun obtener(): Establecimiento

    @Update
    fun actualizar(establecimiento: Establecimiento)
}