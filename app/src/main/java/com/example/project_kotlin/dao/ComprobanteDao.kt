package com.example.project_kotlin.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.project_kotlin.entidades.Comanda
import com.example.project_kotlin.entidades.Comprobante
import com.example.project_kotlin.entidades.ComprobanteComandaYEmpleadoYCajaYTipoComprobanteYMetodoPago

@Dao
interface ComprobanteDao {
    @Query("SELECT * FROM comprobante")
     fun obtenerTodoLiveData() : LiveData<List<ComprobanteComandaYEmpleadoYCajaYTipoComprobanteYMetodoPago>>
    @Query("SELECT * FROM comprobante")
    fun obtenerTodo(): List<ComprobanteComandaYEmpleadoYCajaYTipoComprobanteYMetodoPago>

    @Query("SELECT * FROM comprobante WHERE id = :id")
     fun obtenerPorId(id: Long) : Comprobante
    @Query("SELECT * FROM comprobante WHERE empleado_id = :id")
    fun ComprobantesEmpleado(id: Int) : List<Comprobante>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun guardar(comprobante: Comprobante) : Long

    @Update
     fun actualizar(comprobante: Comprobante)

    @Delete
     fun eliminar(comprobante: Comprobante)
}