package com.example.project_kotlin.dao

import androidx.room.*
import com.example.project_kotlin.entidades.Comprobante
import com.example.project_kotlin.entidades.Plato

@Dao
interface PlatoDao {
    @Query("select * from Plato")
    fun obtenerTodo(): List<Plato>

    @Query("select * from Plato where id_cat_plato = :id_cat_plato")
    fun obtenerPlatosPorCategoria(id_cat_plato: String): List<Plato>

    @Query("SELECT * FROM Plato WHERE id = :id")
    fun obtenerPorId(id: Long) : Plato

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun guardar(plato: Plato)

    @Update
    fun actualizar(plato: Plato)

    @Delete
    fun eliminar(plato:Plato)
}