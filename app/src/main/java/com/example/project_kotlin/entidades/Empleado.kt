package com.example.project_kotlin.entidades

import androidx.annotation.NonNull
import androidx.room.*
import java.util.*

@Entity(tableName = "Empleado",
    foreignKeys = [
        ForeignKey(
            entity = Cargo::class,
            parentColumns = ["id"],
            childColumns = ["cargo_id"]
        ),
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"]
        )
    ])
class Empleado(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @NonNull @ColumnInfo(name="nombre") var nombreEmpleado : String,
    @NonNull @ColumnInfo(name="apellido") var apellidoEmpleado : String,
    @NonNull @ColumnInfo(name="telefono") var telefonoEmpleado : String,
    @NonNull @ColumnInfo(name="dni") var dniEmpleado : String,
    @ColumnInfo(name = "fecha_registro", defaultValue = "CURRENT_TIMESTAMP") var fechaRegistro: Date = Date(),
    ):java.io.Serializable {
    @Embedded(prefix = "cargo_")
    lateinit var cargo: Cargo
    @Embedded(prefix = "usuario_")
    lateinit var usuario: Usuario
}