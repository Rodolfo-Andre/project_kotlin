package com.example.project_kotlin.entidades

import androidx.room.*

@Entity(tableName = "caja"/*,
    foreignKeys = [ForeignKey(
        entity = Establecimiento::class,
        parentColumns = ["id"],
        childColumns = ["establecimiento_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index("establecimiento_id")
    ]*/)
data class Caja (
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    //@ColumnInfo(name = "establecimiento_id") val establecimientoId
) {
}

data class CajaConAperturas(
    @Embedded val caja: Caja,
    @Relation(
        parentColumn = "id",
        entityColumn = "caja_id"
    )
    val aperturas: List<Apertura>
)