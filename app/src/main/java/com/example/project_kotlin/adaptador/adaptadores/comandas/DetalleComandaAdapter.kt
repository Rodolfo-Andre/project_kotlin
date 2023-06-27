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
import com.example.project_kotlin.entidades.DetalleComandaConPlato
import com.example.project_kotlin.utils.appConfig
import com.example.project_kotlin.vistas.categoria_platos.EditCatPlatoActivity
import com.example.project_kotlin.vistas.comandas.EditarComanda
import com.example.project_kotlin.vistas.comandas.RegistrarComanda

class DetalleComandaAdapter (var info :  List<DetalleComandaConPlato>, private var listener: OnItemClickLister? = null)
    : RecyclerView.Adapter<VistaDetalleItemComanda>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaDetalleItemComanda {
        val vista = LayoutInflater.from(parent.context).
        inflate(R.layout.item_detallecomandas,parent,false)

        return VistaDetalleItemComanda(vista)
    }

    override fun getItemCount(): Int = info.size


    override fun onBindViewHolder(holder: VistaDetalleItemComanda, position: Int) {

        //debo cambiar por el nombre del plato
        holder.tvPlatoListD.text = info.get(position).plato.nombrePlato
        holder.tvCantidadListD.text = info.get(position).detalle.cantidadPedido.toString()
        holder.tvObservacionListD.text = info.get(position).detalle.observacion
        holder.tvPrecioPlato.text = info.get(position).plato.precioPlato.toString()
        var context = holder.itemView.context
        holder.listCEliminarP.setOnClickListener{
            listener?.onItemDeleteClick(info.get(position))
        }
        holder.listCEditarP.setOnClickListener{
            listener?.onItemUpdateClick(info.get(position))
        }

    }

    fun actualizarDetalleComanda(info:List<DetalleComandaConPlato>){
        this.info = info as ArrayList<DetalleComandaConPlato>
        notifyDataSetChanged()
    }
    interface OnItemClickLister {
        fun onItemDeleteClick(detalle: DetalleComandaConPlato)
        fun onItemUpdateClick(detalle: DetalleComandaConPlato)
    }



}
