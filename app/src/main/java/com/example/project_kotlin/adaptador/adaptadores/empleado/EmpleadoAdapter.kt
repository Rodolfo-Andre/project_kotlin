package com.example.project_kotlin.adaptador.adaptadores.empleado

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.adaptador.vistas.empleados.VistaItemUsuario
import com.example.project_kotlin.entidades.Empleado
import com.example.project_kotlin.utils.appConfig
import com.example.project_kotlin.vistas.empleados.ActualizarEmpleado

class EmpleadoAdapter(var info:List<Empleado>):RecyclerView.Adapter<VistaItemUsuario>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaItemUsuario {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_usuario, parent, false)
        Log.d("Ingresar", "Empleado")
        return VistaItemUsuario(vista)
    }

    override fun getItemCount(): Int = info.size

    override fun onBindViewHolder(holder: VistaItemUsuario, position: Int) {
        holder.tvApeUsu.text =  info.get(position).apellidoEmpleado
        holder.tvCargoUsu.text = info.get(position).cargo.cargo
        holder.tvCorreoUsu.text = info.get(position).usuario.correo
        holder.tvDniUsu.text = info.get(position).dniEmpleado
        holder.tvNomUsu.text = info.get(position).nombreEmpleado
        holder.tvTelfUsu.text = info.get(position).telefonoEmpleado
        holder.tvFechaUsu.text = info.get(position).fechaRegistro
        Log.d("QUE", "EN EL AIRE WEON")
        var context = holder.itemView.context

        holder.itemView.setOnClickListener{
            Log.d("EmpleadoAdapter", "Prueba de click")
            var intent = Intent(appConfig.CONTEXT,ActualizarEmpleado::class.java )
            intent.putExtra("empleado", info[position])
            ContextCompat.startActivity(context, intent, null)

        }
    }
    fun actualizarListaEmpleados(info:List<Empleado>){
        this.info = info
        notifyDataSetChanged()
    }
}