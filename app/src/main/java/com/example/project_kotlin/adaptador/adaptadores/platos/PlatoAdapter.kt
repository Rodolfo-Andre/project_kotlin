package com.example.project_kotlin.adaptador.adaptadores.platos

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.adaptador.vistas.platos.VistaItemPlato
import com.example.project_kotlin.entidades.Plato
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat
import com.example.project_kotlin.utils.appConfig

import com.example.project_kotlin.vistas.platos.ActualizarPlato

class PlatoAdapter(var info: List<Plato>) : RecyclerView.Adapter<VistaItemPlato>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaItemPlato {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_platos, parent, false)

        return VistaItemPlato(vista)
    }

    override fun getItemCount(): Int =  info.size


    override fun onBindViewHolder(holder: VistaItemPlato, position: Int) {



        holder.tvCodPlato.text = info.get(position).id
        holder.tvNombrePlato.text = info.get(position).nombrePlato
        holder.tvPrecioPlato.text = info.get(position).precioPlato.toString()
        holder.tvCatNomPlato.text = info.get(position).categoriaPlato.categoria

        var imageData = info.get(position).nombreImagen
        var bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
        holder.imagenPlato.setImageBitmap(bitmap)


        var context = holder.itemView.context

        holder.itemView.setOnClickListener{
            var intent = Intent(appConfig.CONTEXT, ActualizarPlato::class.java)
            intent.putExtra("plato",info[position])
            ContextCompat.startActivity(context, intent, null)
        }

    }

    fun actulizarPlatos(info:List<Plato>){
        this.info = info
        notifyDataSetChanged()
    }
}