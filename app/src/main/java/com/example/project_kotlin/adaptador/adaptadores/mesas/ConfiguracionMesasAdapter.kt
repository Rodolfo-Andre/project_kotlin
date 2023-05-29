package com.example.project_kotlin.adaptador.adaptadores

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope

import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.adaptador.vistas.VistaItemAgregarMesa
import com.example.project_kotlin.entidades.Mesa
import com.example.project_kotlin.utils.appConfig
import com.example.project_kotlin.vistas.ActualizarMesas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConfiguracionMesasAdapter(val info:ArrayList<Mesa>):RecyclerView.Adapter<VistaItemAgregarMesa>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaItemAgregarMesa {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_agregar_mesa, parent, false)
        return VistaItemAgregarMesa(vista)
    }

    override fun getItemCount(): Int {
        return info.size
    }

    override fun onBindViewHolder(holder: VistaItemAgregarMesa, position: Int) {
        holder.tvID.text = "" + info.get(position).id
        holder.tvCanAsientos.text = "" + info.get(position).cantidadAsientos
        holder.tvEstadoMesa.text = "" + info.get(position).estadoMesa
        var context = holder.itemView.context

        holder.itemView.setOnClickListener{

            var intent = Intent(appConfig.CONTEXT, ActualizarMesas::class.java)
            intent.putExtra("mesa", info[position])
            ContextCompat.startActivity(context, intent, null)
        }

    }
}