package com.example.project_kotlin.adaptador.adaptadores

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.adaptador.vistas.VistaItemPlato
import com.example.project_kotlin.entidades.Plato

class PlatoAdapter(val info:ArrayList<Plato>)
    :RecyclerView.Adapter<VistaItemPlato>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaItemPlato {
        val vista = LayoutInflater.from(parent.context).
        inflate(R.layout.item_platos,parent,false)

        return VistaItemPlato(vista)
    }

    override fun getItemCount(): Int {
       return info.size
    }

    override fun onBindViewHolder(holder: VistaItemPlato, position: Int) {
        holder.tvCodPlato.text= info.get(position).id
        holder.tvNombrePlato.text= info.get(position).nombrePlato
        holder.tvPrecioPlato.text = info.get(position).precioPlato.toString()
        holder.tvCatNomPlato.text = info.get(position).idCatPlato
    }

}