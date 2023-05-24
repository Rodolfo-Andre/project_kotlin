package com.example.project_kotlin.dao

import androidx.room.*
import com.example.project_kotlin.entidades.Mesa

@Dao
interface MesaDao {
    @Query("SELECT * FROM mesa")
     fun obtenerTodo() : ArrayList<Mesa>

    @Query("SELECT * FROM mesa WHERE id = :id")
     fun obtenerPorId(id: Long) : Mesa

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun guardar(mesa: Mesa) : Long

    @Update
     fun actualizar(mesa: Mesa)

    @Delete
     fun eliminar(mesa: Mesa)
}