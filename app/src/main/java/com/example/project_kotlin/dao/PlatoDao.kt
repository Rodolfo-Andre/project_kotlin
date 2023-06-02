package com.example.project_kotlin.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.project_kotlin.entidades.Comprobante
import com.example.project_kotlin.entidades.Establecimiento
import com.example.project_kotlin.entidades.Plato

@Dao
interface PlatoDao {
    @Query("select * from Plato")
    fun obtenerTodo(): List<Plato>

    @Query("select * from Plato")
    fun obtenerTodoLiveData(): LiveData<List<Plato>>

    @Query("select * from Plato where catplato_id = :catplato_id")
    fun obtenerPlatosPorCategoria(catplato_id: String): List<Plato>

    @Query("SELECT * FROM Plato WHERE id = :id")
    fun obtenerPorId(id: String) : Plato

    @Insert
    fun guardar(plato: Plato)

    @Update
    fun actualizar(plato: Plato)

    @Delete
    fun eliminar(plato:Plato)
}