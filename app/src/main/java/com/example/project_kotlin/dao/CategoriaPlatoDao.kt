package com.example.project_kotlin.dao

import androidx.room.*
import com.example.project_kotlin.entidades.CategoriaPlato

@Dao
interface CategoriaPlatoDao {
    @Query("SELECT * FROM categoria_plato")
     fun obtenerTodo() : List<CategoriaPlato>

    @Query("SELECT * FROM categoria_plato WHERE id = :id")
     fun obtenerPorId(id: String) : CategoriaPlato

    @Insert
     fun guardar(categoriaPlato: CategoriaPlato)

    @Update
     fun actualizar(categoriaPlato: CategoriaPlato)

    @Delete
     fun eliminar(categoriaPlato: CategoriaPlato)
}