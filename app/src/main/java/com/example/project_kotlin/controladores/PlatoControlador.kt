package com.example.project_kotlin.controladores


import com.example.project_kotlin.ComandaApplication
import com.example.project_kotlin.entidades.Plato


class PlatoControlador (private val aplicacion: ComandaApplication) {
    private val database by lazy { aplicacion.database }
    private val platoDao by lazy { database.platoDao() }

    fun listado(): List<Plato>{
        var plato = platoDao.obtenerTodo()
        return plato
    }

}