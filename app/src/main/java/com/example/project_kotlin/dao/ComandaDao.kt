package com.example.project_kotlin.dao

import androidx.room.*
import com.example.project_kotlin.entidades.Comanda

@Dao
interface ComandaDao {
    @Query("SELECT * FROM comanda")
     fun obtenerTodo() : List<Comanda>

    @Query("SELECT * FROM comanda WHERE id = :id")
     fun obtenerPorId(id: Long) : Comanda

     @Query("SELECT * FROM comanda WHERE mesa_id = :mesa_id")
     fun obtenerComandasPorMesa(mesa_id: Int) : List<Comanda>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun guardar(comanda: Comanda) : Long

    @Update
     fun actualizar(comanda: Comanda)

    @Delete
     fun eliminar(comanda: Comanda)
}