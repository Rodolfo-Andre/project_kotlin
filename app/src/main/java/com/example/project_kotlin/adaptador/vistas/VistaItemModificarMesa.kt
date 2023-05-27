package com.example.project_kotlin.adaptador.vistas

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R

class VistaItemModificarMesa(itemView: View): RecyclerView.ViewHolder(itemView) {

    var tvCanModMesa:TextView

    init {
        tvCanModMesa = itemView.findViewById(R.id.tvCanModMesa)
    }
}