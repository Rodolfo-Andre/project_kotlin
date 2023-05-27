package com.example.project_kotlin.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.project_kotlin.entidades.EstadoComanda

@Dao
interface EstadoComandaDao {

    @Query("select * from EstadosComanda")
    fun obtenerTodo() : List<EstadoComanda>
}