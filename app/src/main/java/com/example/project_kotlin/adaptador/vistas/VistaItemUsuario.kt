package com.example.project_kotlin.adaptador.vistas

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R

class VistaItemUsuario(itemView: View):RecyclerView.ViewHolder(itemView) {

    var tvNomUsu:TextView
    var tvApeUsu:TextView
    var tvDniUsu:TextView
    var tvCorreoUsu:TextView
    var tvTelfUsu:TextView
    var tvCargoUsu:TextView

    init {
        tvNomUsu = itemView.findViewById(R.id.tvNomUsu)
        tvApeUsu = itemView.findViewById(R.id.tvApeUsu)
        tvDniUsu = itemView.findViewById(R.id.tvDniUsu)
        tvCorreoUsu = itemView.findViewById(R.id.tvCorreoUsu)
        tvTelfUsu = itemView.findViewById(R.id.tvTelfUsu)
        tvCargoUsu = itemView.findViewById(R.id.tvCargoUsu)

    }
}


