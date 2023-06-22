package com.example.project_kotlin.adaptador.adaptadores.comandas


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.adaptador.vistas.comandas.VistaItemComanda
import com.example.project_kotlin.entidades.CategoriaPlato

import com.example.project_kotlin.entidades.Comanda
import com.example.project_kotlin.utils.appConfig
import com.example.project_kotlin.vistas.categoria_platos.EditCatPlatoActivity
import com.example.project_kotlin.vistas.comandas.EditarComanda

class ComandaAdapter (var info :  List<Comanda>)
    : RecyclerView.Adapter<VistaItemComanda>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaItemComanda {
        val vista = LayoutInflater.from(parent.context).
        inflate(R.layout.item_comandas,parent,false)

        return VistaItemComanda(vista)
    }

    override fun getItemCount(): Int = info.size


    override fun onBindViewHolder(holder: VistaItemComanda, position: Int) {
        holder.tvComandaIdList.text = info.get(position).id.toString()
        holder.tvMesaIdList.text = info.get(position).mesaId.toString()
        //no existe dato de fecha en la entidad
        holder.tvFechaCList.text = info.get(position).cantidadAsientos.toString()
        holder.tvEstadoList.text = info.get(position).estadoComandaId.toString()
        var context = holder.itemView.context

        holder.itemView.setOnClickListener{

            var intent = Intent(appConfig.CONTEXT, EditarComanda::class.java)
            intent.putExtra("Comanda",info[position])
            ContextCompat.startActivity(context, intent, null)

        }

    }


    fun actualizarComanda(info:List<Comanda>){

        this.info = info as ArrayList<Comanda>
        notifyDataSetChanged()

    }



}