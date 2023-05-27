package com.example.project_kotlin.adaptador.vistas

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R

class VistaItemPlato(itemView: View):RecyclerView.ViewHolder(itemView) {

    var tvCodPlato:TextView
    var tvNombrePlato:TextView
    var tvPrecioPlato:TextView

    init {
        tvCodPlato = itemView.findViewById(R.id.tvCodPlato)
        tvNombrePlato = itemView.findViewById(R.id.tvNombrePlato)
        tvPrecioPlato = itemView.findViewById(R.id.tvPrecioPlato)
    }
}