package com.example.project_kotlin.dao

import androidx.room.*
import com.example.project_kotlin.entidades.Apertura

@Dao
interface AperturaDao {
    @Query("SELECT * FROM apertura")
     fun obtenerTodo() : List<Apertura>

    @Query("SELECT * FROM apertura WHERE id = :id")
     fun obtenerPorId(id: Long) : Apertura

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun guardar(apertura: Apertura) : Long

    @Update
     fun actualizar(apertura: Apertura)

    @Delete
     fun eliminar(apertura: Apertura)
}