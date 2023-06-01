package com.example.project_kotlin.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Establecimiento")
class
Establecimiento(
    @PrimaryKey (autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name="nom_Establecimiento") var nombreEstablecimiento: String,
    @ColumnInfo(name="telefono_establecimiento") var telefonoEstablecimiento: String,
    @ColumnInfo(name="direccion_establecimiento") var direccionEstablecimiento: String,
    @ColumnInfo(name = "ruc_establecimiento") var rucEstablecimiento : String
):java.io.Serializable{
}