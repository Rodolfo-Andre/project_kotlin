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
        childColumns = ["tipocomprobante_id"],
        onDelete = ForeignKey.CASCADE)
        , ForeignKey(
        entity = Empleado::class,
        parentColumns = ["id"],
        childColumns = ["empleado_id"],
        onDelete = ForeignKey.CASCADE),
        ForeignKey(
            entity = MetodoPago::class,
            parentColumns = ["id"],
            childColumns = ["metodopago_id"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [
        Index("comanda_id"),
        Index("caja_id"),
        Index("establecimiento_id"),
        Index("tipocomprobante_id"),
        Index("empleado_id"),
        Index("metodopago_id")
    ])
data class Comprobante (
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "nombre_cliente", defaultValue = "Cliente") var nombreCliente: String = "Cliente",
    @ColumnInfo(name = "fecha_emision", defaultValue = "CURRENT_TIMESTAMP") var fechaEmision: Date = Date(),
    @ColumnInfo(name = "precio_total_pedido") var precioTotalPedido: Double = 0.0):java.io.Serializable{

    @Embedded(prefix ="caja_")
    lateinit var caja: Caja
    @Embedded(prefix ="establecimiento_")
    lateinit var establecimiento: Establecimiento
    @Embedded(prefix ="comanda_")
    lateinit var comanda: Comanda
    @Embedded(prefix ="tipocomprobante_")
    lateinit var tipoComprobante: TipoComprobante
    @Embedded(prefix ="empleado_")
    lateinit var empleado: Empleado

    @Embedded(prefix = "metodopago_")
    lateinit var metodoPago: MetodoPago

}

