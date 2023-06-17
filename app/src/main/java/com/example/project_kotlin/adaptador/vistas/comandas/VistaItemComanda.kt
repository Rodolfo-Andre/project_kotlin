package com.example.project_kotlin.adaptador.vistas.comandas

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R

class VistaItemComanda (itemView: View): RecyclerView.ViewHolder(itemView) {
    //Atributos
    var tvComandaIdList: TextView
    var tvMesaIdList: TextView
    var tvFechaCList: TextView
    var tvEstadoList: TextView
    var listCEditar : ImageButton
    var listCEliminar : ImageButton
    var listCFacturar : ImageButton


    //Vincular
    init {
        tvComandaIdList = itemView.findViewById(R.id.tvComandaIdList)
        tvMesaIdList = itemView.findViewById(R.id.tvMesaIdList)
        tvFechaCList = itemView.findViewById(R.id.tvFechaCList)
        tvEstadoList = itemView.findViewById(R.id.tvEstadoList)
        listCEditar = itemView.findViewById(R.id.listCEditar)
        listCEliminar = itemView.findViewById(R.id.listCEliminar)
        listCFacturar = itemView.findViewById(R.id.listCFacturar)
    }
}