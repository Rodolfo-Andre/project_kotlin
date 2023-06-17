package com.example.project_kotlin.adaptador.adaptadores.comandas


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.adaptador.vistas.comandas.VistaDetalleItemComanda
import com.example.project_kotlin.adaptador.vistas.comandas.VistaItemComanda
import com.example.project_kotlin.entidades.CategoriaPlato

import com.example.project_kotlin.entidades.DetalleComanda
import com.example.project_kotlin.utils.appConfig
import com.example.project_kotlin.vistas.categoria_platos.EditCatPlatoActivity
import com.example.project_kotlin.vistas.comandas.EditarComanda
import com.example.project_kotlin.vistas.comandas.RegistrarComanda

class DetalleComandaAdapter (var info :  List<DetalleComanda>)
    : RecyclerView.Adapter<VistaDetalleItemComanda>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaDetalleItemComanda {
        val vista = LayoutInflater.from(parent.context).
        inflate(R.layout.item_detallecomandas,parent,false)

        return VistaDetalleItemComanda(vista)
    }

    override fun getItemCount(): Int = info.size


    override fun onBindViewHolder(holder: VistaDetalleItemComanda, position: Int) {
        holder.tvDetalleidListD.text = info.get(position).id.toString()
        //debo cambiar por el nombre del plato
        holder.tvPlatoListD.text = info.get(position).idPlato.toString()
        holder.tvCantidadListD.text = info.get(position).cantidadPedido.toString()
        var context = holder.itemView.context

        holder.itemView.setOnClickListener{

            var intent = Intent(appConfig.CONTEXT, RegistrarComanda::class.java)
            intent.putExtra("DetalleComanda",info[position])
            ContextCompat.startActivity(context, intent, null)

        }

    }


    fun actualizarDetalleComanda(info:List<DetalleComanda>){

        this.info = info as ArrayList<DetalleComanda>
        notifyDataSetChanged()

    }



}