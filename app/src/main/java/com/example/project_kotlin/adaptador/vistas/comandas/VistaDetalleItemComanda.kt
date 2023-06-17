package com.example.project_kotlin.adaptador.vistas.comandas

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R

class VistaDetalleItemComanda (itemView: View): RecyclerView.ViewHolder(itemView) {

    var tvDetalleidListD : TextView
    var tvPlatoListD: TextView
    var tvCantidadListD: TextView
    var tvObservacionListD : TextView
    var listCEditarP : ImageButton
    var listCEliminarP : ImageButton

    init {
        tvDetalleidListD = itemView.findViewById(R.id.tvDetalleidListD)
        tvPlatoListD = itemView.findViewById(R.id.tvPlatoListD)
        tvCantidadListD = itemView.findViewById(R.id.tvCantidadListD)
        tvObservacionListD = itemView.findViewById(R.id.tvObservacionListD)
        listCEditarP = itemView.findViewById(R.id.listCEditarP)
        listCEliminarP = itemView.findViewById(R.id.listCEliminarP)
    }

}