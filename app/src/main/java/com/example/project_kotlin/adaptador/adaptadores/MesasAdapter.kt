package com.example.project_kotlin.adaptador.adaptadores

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.adaptador.vistas.VistaMesaCRUD
import com.example.project_kotlin.entidades.Mesa

class mesasAdapter(val info:ArrayList<Mesa>): RecyclerView.Adapter<VistaMesaCRUD>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaMesaCRUD {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
       return info.size
    }

    override fun onBindViewHolder(holder: VistaMesaCRUD, position: Int) {
        TODO("Not yet implemented")
    }
}