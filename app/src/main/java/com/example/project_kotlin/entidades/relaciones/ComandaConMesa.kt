package com.example.project_kotlin.entidades.relaciones

import androidx.room.Embedded
import androidx.room.Relation
import com.example.project_kotlin.entidades.Comanda
import com.example.project_kotlin.entidades.Mesa

class ComandaConMesa (@Embedded val comanda: Comanda,
        @Relation(
            parentColumn = "mesa_id",
            entityColumn = "id"
        )
        val mesa: Mesa)