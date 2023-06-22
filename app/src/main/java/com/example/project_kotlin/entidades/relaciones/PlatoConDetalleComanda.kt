package com.example.project_kotlin.entidades.relaciones

import androidx.room.Embedded
import androidx.room.Relation
import com.example.project_kotlin.entidades.DetalleComanda
import com.example.project_kotlin.entidades.Plato

class PlatoConDetalleComanda (
    @Embedded val plato: Plato,
    @Relation(
        parentColumn = "id",
        entityColumn = "plato_id"
    )
    val detalleComanda: List<DetalleComanda>
)