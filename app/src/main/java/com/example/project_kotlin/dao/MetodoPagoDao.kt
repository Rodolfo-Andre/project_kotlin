package com.example.project_kotlin.dao

import androidx.room.*
import com.example.project_kotlin.entidades.Mesa
import com.example.project_kotlin.entidades.MetodoPago

@Dao
interface MetodoPagoDao {
    @Query("SELECT * FROM metodo_pago")
    fun buscarTodo() : List<MetodoPago>

    @Query("SELECT * FROM metodo_pago WHERE id = :id")
    fun buscarPorId(id: Long) : MetodoPago

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registrar(metodoPago: MetodoPago) : Long

    @Update
    fun actualizar(metodoPago: MetodoPago)

    @Delete
    fun eliminar(metodoPago: MetodoPago)
}