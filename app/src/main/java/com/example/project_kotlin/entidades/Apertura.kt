package com.example.project_kotlin.entidades

import androidx.room.*
import java.util.*

@Entity(tableName = "apertura",
    foreignKeys = [ForeignKey(
        entity = Caja::class,
        parentColumns = ["id"],
        childColumns = ["caja_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index("caja_id")
    ])
data class Apertura (
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "fecha_apertura", defaultValue = "CURRENT_TIMESTAMP") var fechaApertura: Date = Date(),
    @ColumnInfo(name = "fecha_cierre") var fechaCierre: Date? = null,
    @ColumnInfo(name = "venta_total_dia") var ventaTotalDia: Double = 0.0,
    //@ColumnInfo(name = "empleado_id") var empleadoId: Int,
    @ColumnInfo(name = "caja_id") var cajaId: Int)
{
}
