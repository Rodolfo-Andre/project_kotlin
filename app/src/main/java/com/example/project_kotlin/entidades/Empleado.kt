package com.example.project_kotlin.entidades

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Empleado",
    foreignKeys = [
        ForeignKey(
            entity = Cargo::class,
            parentColumns = ["id"],
            childColumns = ["id_cargo"]
        ),
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["id_usuario"]
        )
    ])
class Empleado(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @NonNull @ColumnInfo(name="nombre") var nombreEmpleado : String,
    @NonNull @ColumnInfo(name="apellido") var apellidoEmpleado : String,
    @NonNull @ColumnInfo(name="telefono") var telefonoEmpleado : String,
    @NonNull @ColumnInfo(name="dni") var dniEmpleado : String,
    @NonNull @ColumnInfo(name = "fecha_registro", defaultValue = "CURRENT_TIMESTAMP") var fechaRegistro: Date = Date(),
    @NonNull @ColumnInfo(name="id_cargo") var idCargo : Int,
    @ColumnInfo(name="id_usuario") var idUsuario : Int
    ) {
}