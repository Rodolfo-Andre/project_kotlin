package com.example.project_kotlin.dao

import androidx.room.*
import com.example.project_kotlin.entidades.Empleado
import com.example.project_kotlin.entidades.Plato

@Dao
interface EmpleadoDao {

    @Query("select * from Empleado")
    fun obtenerTodo(): List<Empleado>

    @Query("select * from Empleado where id_cargo = :id_cargo")
    fun buscarPorCargo(id_cargo: String): List<Empleado>

    @Query("SELECT * FROM Empleado WHERE id = :id")
    fun obtenerPorId(id: Long) : Empleado

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun guardar(empleado: Empleado) : Long

    @Update
    fun actualizar(empleado: Empleado)

    @Delete
    fun eliminar(empleado: Empleado)
}