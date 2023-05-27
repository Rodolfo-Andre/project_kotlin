package com.example.project_kotlin.dao

import com.example.project_kotlin.entidades.Plato
interface PlatoDao {
    @Query("SELECT * FROM plato")
    fun obtenerTodo() : List<Plato>

    @Query("SELECT * FROM plato WHERE id = :id")
    fun obtenerPorId(id: String) : Plato

    @Insert
    fun guardar(plato: Plato)

    @Update
    fun actualizar(plato: Plato)

    @Delete
    fun eliminar(plato: Plato)
}