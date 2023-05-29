package com.example.project_kotlin.utils

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.project_kotlin.db.ComandaDatabase

//QUIERO EL CONTEXTO DE LA APLICACIÓN, LO TRABAJARÉ COMO EN CLASE CON ROOM
class appConfig: Application() {
    //Variables globales
    //Deben de estar dentro de este bloque
    companion object{
        lateinit var CONTEXT: Context
        lateinit var db : ComandaDatabase

    }



    override fun onCreate() {
        super.onCreate()
        CONTEXT=applicationContext
        db = Room.databaseBuilder(
            CONTEXT, ComandaDatabase::class.java, "comanda_database").build()
    }
}