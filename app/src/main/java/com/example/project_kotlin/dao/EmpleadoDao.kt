package com.example.project_kotlin.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.project_kotlin.entidades.Empleado
import com.example.project_kotlin.entidades.Plato

@Dao
interface EmpleadoDao {

    @Query("select * from Empleado")
    fun obtenerTodoLiveData(): LiveData<List<Empleado>>

    @Query("select * from Empleado")
    fun obtenerTodo(): List<Empleado>

    @Query("select * from Empleado where cargo_id = :cargo_id")
    fun buscarPorCargo(cargo_id: String): List<Empleado>

    @Query("SELECT * FROM Empleado WHERE id = :id")
    fun obtenerPorId(id: Long) : Empleado

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun guardar(empleado: Empleado) : Long

    @Update
    fun actualizar(empleado: Empleado)

    @Delete
    fun eliminar(empleado: Empleado)
}