package com.example.project_kotlin.entidades

import androidx.room.*

@Entity(tableName = "categoria_plato")
data class CategoriaPlato (
    @PrimaryKey var id: String = "",
    var categoria: String):java.io.Serializable {
    companion object {
        fun generarCodigo(listCatDish: List<CategoriaPlato>): String {
            if (listCatDish.isEmpty()) return "C-001"

            val ultimoCodigo = listCatDish.last().id
            val numero = ultimoCodigo.split('-')[1].toInt() + 1

            return "C-${String.format("%03d", numero)}"
        }
    }
}