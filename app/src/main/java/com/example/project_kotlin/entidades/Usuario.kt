package com.example.project_kotlin.entidades

import androidx.room.*

@Entity(tableName = "usuario")
data class Usuario (
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var correo: String,
    var contrasena: String,
    @ColumnInfo(name = "codigo_recuperacion", defaultValue = "0") var codigoRecuperacion: Int = 0) {
}