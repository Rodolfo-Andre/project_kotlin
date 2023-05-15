package com.example.project_kotlin.dao

import androidx.room.*
import com.example.project_kotlin.entidades.Comprobante

@Dao
interface ComprobanteDao {
    @Query("SELECT * FROM comprobante")
     fun obtenerTodo() : List<Comprobante>

    @Query("SELECT * FROM comprobante WHERE id = :id")
     fun obtenerPorId(id: Long) : Comprobante

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun guardar(comprobante: Comprobante) : Long

    @Update
     fun actualizar(comprobante: Comprobante)

    @Delete
     fun eliminar(comprobante: Comprobante)
}