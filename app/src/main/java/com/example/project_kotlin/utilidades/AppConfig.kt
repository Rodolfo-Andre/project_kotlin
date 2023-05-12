package com.example.project_kotlin.utilidades

import android.app.Application
import android.content.Context

class appConfig:Application() {
    companion object{
        lateinit var CONTEXT: Context
        var BD_NAME = "sistemaVentas"
        var VERSION = 1
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
    }
}