package com.example.project_kotlin.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import com.example.project_kotlin.R
import com.example.project_kotlin.vistas.mesas.DatosMesas

class MainActivity : AppCompatActivity(), OnClickListener {
    lateinit var btnIngresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnIngresar = findViewById(R.id.btnIngresar)
        btnIngresar.setOnClickListener({vincular()})

    }
    fun vincular(){
        var intent = Intent(this, DatosMesas::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View?) {

    }
}