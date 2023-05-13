package com.example.project_kotlin.adaptador.vistas

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R

class vistaItemComanda (itemView: View): RecyclerView.ViewHolder(itemView) {
    //Atributos
    var tvNumComandaG:TextView
    var tvEstadoComanda:TextView
    var btnEditarComanda:Button
    var btnEliminarComanda:Button
    var btnVerComanda:Button

    //Vincular
    init {
        tvNumComandaG = itemView.findViewById(R.id.tvNumComandaG)
        tvEstadoComanda = itemView.findViewById(R.id.tvEstadoComanda)
        btnEditarComanda = itemView.findViewById(R.id.btnEditarComanda)
        btnEliminarComanda = itemView.findViewById(R.id.btnEliminarComanda)
        btnVerComanda = itemView.findViewById(R.id.btnVerComanda)
    }
}