package com.example.project_kotlin.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "Detalle_Comanda",
    foreignKeys = [
        ForeignKey(
            entity = Comanda::class,
            parentColumns = ["id"],
            childColumns = ["comanda_id"]
        ),
        ForeignKey(
            entity = Plato::class,
            parentColumns = ["id"],
            childColumns = ["id_plato"]
        )
])
class DetalleComanda (
    @PrimaryKey(autoGenerate = true) var id : Long,
    @ColumnInfo("comanda_id") var comandaId: Int,
    @NotNull @ColumnInfo("id_plato") var idPlato : String,
    @NotNull @ColumnInfo("cantidad_pedido") var cantidadPedido : Int,
    @NotNull @ColumnInfo("precioUnitario") var precioUnitario : Double,
    @NotNull @ColumnInfo("observacion") var observacion : String
):java.io.Serializable {
}