package com.example.project_kotlin.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.ComandaApplication
import com.example.project_kotlin.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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