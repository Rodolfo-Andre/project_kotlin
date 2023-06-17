package com.example.project_kotlin.entidades.dto

import com.example.project_kotlin.entidades.*


class ComprobanteDTO( var id:Long=0, var fechaEmision:String ="",var precioTotalPedido:Double=0.00,var igv:Double=0.00, var subTotal:Double=0.00,
                      var descuento:Double=0.00, var nombre:String, var comanda:Comanda,var emmpleado:Empleado, var caja:Caja, var metodoPago:MetodoPago,
                      var tipoComprobante: TipoComprobante):java.io.Serializable{

}





