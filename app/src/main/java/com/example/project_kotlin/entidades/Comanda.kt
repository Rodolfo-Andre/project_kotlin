package com.example.project_kotlin.entidades

import androidx.room.*

@Entity(tableName = "comanda",
    foreignKeys = [
        ForeignKey(
        entity = Mesa::class,
        parentColumns = ["id"],
        childColumns = ["mesa_id"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = EstadoComanda::class,
        parentColumns = ["id"],
        childColumns = ["estado_comanda_id"],
        onDelete = ForeignKey.CASCADE)
        , ForeignKey(
        entity = Empleado::class,
        parentColumns = ["id"],
        childColumns = ["empleado_id"],
        onDelete = ForeignKey.CASCADE)],
    indices = [
        Index("mesa_id"),
        Index("estado_comanda_id"),
        Index("empleado_id"),
    ])
data class Comanda (
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "cantidad_asientos") var cantidadAsientos: Int,
    @ColumnInfo(name = "precio_total") var precioTotal: Double = 0.0,
    @ColumnInfo(name = "mesa_id") var mesaId: Int,
    @ColumnInfo(name = "estado_comanda_id") var estadoComandaId: Int,
    @ColumnInfo(name = "empleado_id") var empleadoId: Int,
) {
}