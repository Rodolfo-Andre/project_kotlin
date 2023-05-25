package com.example.project_kotlin.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.ComandaApplication
import com.example.project_kotlin.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(){
    private lateinit var btnMesa : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnMesa = findViewById(R.id.btnPruebaMesa2)
        btnMesa.setOnClickListener({vincular()})

    }
    fun vincular(){
        var intent = Intent(this, DatosMesas::class.java)
        startActivity(intent)
    }


}