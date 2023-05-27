package com.example.project_kotlin.adaptador.adaptadores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.adaptador.vistas.VistaItemCategoriaPlato
import com.example.project_kotlin.entidades.CategoriaPlato

class CategoriaPlatoAdapter(val info:ArrayList<CategoriaPlato>)
    :RecyclerView.Adapter<VistaItemCategoriaPlato>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaItemCategoriaPlato {
        val vista = LayoutInflater.from(parent.context).
        inflate(R.layout.item_categoriaplato,parent,false)

        return VistaItemCategoriaPlato(vista)
    }

    override fun getItemCount(): Int {
       return  info.size
    }

    override fun onBindViewHolder(holder: VistaItemCategoriaPlato, position: Int) {
        holder.tvCodCategoriaPlato.text = info.get(position).id
        holder.tvNombreCategoriaPlato.text = info.get(position).categoria
    }
}