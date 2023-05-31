package com.example.project_kotlin.dao

import androidx.room.*
import com.example.project_kotlin.entidades.Comprobante
import com.example.project_kotlin.entidades.Establecimiento
import com.example.project_kotlin.entidades.Mesa

@Dao
interface EstablecimientoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun guardar(establecimiento: Establecimiento) : Long
    @Query("select * from Establecimiento")
    fun obtener(): Establecimiento

    @Query("SELECT * FROM Establecimiento WHERE id = :id")
    fun obtenerPorId(id: Long) : Establecimiento

    @Update
    fun actualizar(establecimiento: Establecimiento)

    @Delete
    fun eliminar(establecimiento: Establecimiento)
}