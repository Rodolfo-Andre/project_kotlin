package com.example.project_kotlin.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Detalle_Comprobante",
foreignKeys = [
    ForeignKey(
        entity = Comprobante::class,
        parentColumns = ["id"],
        childColumns = ["id_boleta"]
    ),
    ForeignKey(
        entity = MetodoPago::class,
        parentColumns = ["id"],
        childColumns = ["id_metodo_pago"]
    )
])
class DetalleComprobante (
    @PrimaryKey(autoGenerate = true) var id : Long = 0,
    @ColumnInfo("id_boleta") var idBoleta : Int,
    @ColumnInfo("id_metodo_pago") var idMetodoPago : Int,
    @ColumnInfo("monto_pago") var montoPago : Double
        ){
}