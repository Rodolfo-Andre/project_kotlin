package com.example.project_kotlin.controladores

import com.example.project_kotlin.ComandaApplication
import com.example.project_kotlin.entidades.CategoriaPlato

class CategoriaPlatoController(private val aplicacion:ComandaApplication){
    private val database by lazy { aplicacion.database}
    private val categoriaPlato by lazy { database.categoriaPlatoDao() }

    fun listado(): List<CategoriaPlato>{
        var categoriaplato = categoriaPlato.obtenerTodo()
        return categoriaplato
    }
}