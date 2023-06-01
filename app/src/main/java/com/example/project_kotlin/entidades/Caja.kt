package com.example.project_kotlin.entidades

import androidx.room.*

@Entity(tableName = "caja",
    foreignKeys = [ForeignKey(
        entity = Establecimiento::class,
        parentColumns = ["id"],
        childColumns = ["establecimiento_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index("establecimiento_id")
    ])
data class Caja (
    @PrimaryKey(autoGenerate = true) var id: Long = 0
) :java.io.Serializable{
    @Embedded(prefix = "establecimiento_")
    var establecimiento: Establecimiento? = null
}


data class CajaConComprobantes(
    @Embedded val caja: Caja,
    @Relation(
        parentColumn = "id",
        entityColumn = "caja_id"
    )
    val comprobantes: List<Comprobante>
)