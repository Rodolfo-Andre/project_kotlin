package com.example.project_kotlin.dao

import androidx.room.*
import com.example.project_kotlin.entidades.DetalleComanda
import com.example.project_kotlin.entidades.DetalleComprobante

@Dao
interface DetalleComprobanteDao {
    @Query("select * from Detalle_Comprobante")
    fun obtenerTodo(): List<DetalleComprobante>

    @Query("select * from Detalle_Comprobante where id_boleta = :id_boleta")
    fun buscarDetallesPorBoleta(id_boleta: Long): List<DetalleComprobante>

    @Query("SELECT * FROM Detalle_Comprobante where id_boleta = :id_boleta AND id_metodo_pago = :id_metodo_pago")
    fun obtenerPorBoletaYMetodoPago(id_boleta: Long, id_metodo_pago: String) : DetalleComprobante

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun guardar(detalleComprobante: DetalleComprobante) : Long


}