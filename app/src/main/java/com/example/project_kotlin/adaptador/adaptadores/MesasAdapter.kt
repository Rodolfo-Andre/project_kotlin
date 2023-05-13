package com.example.project_kotlin.adaptador.adaptadores

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.adaptador.vistas.vistaMesaCRUD
import com.example.project_kotlin.entidades.mesa

class mesasAdapter(val info:ArrayList<mesa>): RecyclerView.Adapter<vistaMesaCRUD>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vistaMesaCRUD {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
       return info.size
    }

    override fun onBindViewHolder(holder: vistaMesaCRUD, position: Int) {
        TODO("Not yet implemented")
    }
}