package com.example.project_kotlin.db

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.project_kotlin.utilidades.appConfig

class dbContext:SQLiteOpenHelper(appConfig.CONTEXT, appConfig.BD_NAME, null, appConfig.VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}