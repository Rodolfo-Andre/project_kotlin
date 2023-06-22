package com.example.project_kotlin.entidades.relaciones

import androidx.room.Embedded
import androidx.room.Relation
import com.example.project_kotlin.entidades.DetalleComanda
import com.example.project_kotlin.entidades.Plato
import com.example.project_kotlin.entidades.PlatoCategoriaPlato

class DetalleComandaConPlato (
    @Embedded val detalleComanda: DetalleComanda,
    @Relation(
        parentColumn = "plato_id",
        entityColumn = "ID"
    )
   val plato:Plato)