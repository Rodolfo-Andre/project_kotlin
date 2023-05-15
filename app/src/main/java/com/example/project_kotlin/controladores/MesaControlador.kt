package com.example.project_kotlin.controladores

import com.example.project_kotlin.ComandaApplication
import com.example.project_kotlin.entidades.Mesa

class MesaControlador (private val aplicacion: ComandaApplication) {
    private val database by lazy { aplicacion.database }
    private val mesaDao by lazy { database.mesaDao() }

    fun listado(): List<Mesa>{
        var mesas = mesaDao.obtenerTodo()
        return mesas
    }
}