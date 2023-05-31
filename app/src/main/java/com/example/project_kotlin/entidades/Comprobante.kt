package com.example.project_kotlin.entidades

import androidx.room.*
import java.util.Date

@Entity(tableName = "comprobante",
    foreignKeys = [
        ForeignKey(
            entity = Comanda::class,
            parentColumns = ["id"],
            childColumns = ["comanda_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Caja::class,
            parentColumns = ["id"],
            childColumns = ["caja_id"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
        entity = Establecimiento::class,
        parentColumns = ["id"],
        childColumns = ["establecimiento_id"],
        onDelete = ForeignKey.CASCADE)
        , ForeignKey(
        entity = TipoComprobante::class,
        parentColumns = ["id"],
        childColumns = ["tipo_comprobante_id"],
        onDelete = ForeignKey.CASCADE)
        , ForeignKey(
        entity = Empleado::class,
        parentColumns = ["id"],
        childColumns = ["empleado_id"],
        onDelete = ForeignKey.CASCADE)],
    indices = [
        Index("comanda_id"),
        Index("caja_id"),
        Index("establecimiento_id"),
        Index("tipo_comprobante_id"),
        Index("empleado_id")
    ])
data class Comprobante (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "nombre_cliente", defaultValue = "Cliente") var nombreCliente: String = "Cliente",
    @ColumnInfo(name = "fecha_emision", defaultValue = "CURRENT_TIMESTAMP") var fechaEmision: Date = Date(),
    @ColumnInfo(name = "precio_total_pedido") var precioTotalPedido: Double = 0.0,
    @ColumnInfo(name = "establecimiento_id") var establecimientoId: Int,
    @ColumnInfo(name = "tipo_comprobante_id") var tipoComprobanteId: Int,
    @ColumnInfo(name = "empleado_id") var empleadoId: Int,
    @ColumnInfo(name = "comanda_id") var comandaId: Int,
    @ColumnInfo(name = "caja_id") var cajaId: Int) {
}

