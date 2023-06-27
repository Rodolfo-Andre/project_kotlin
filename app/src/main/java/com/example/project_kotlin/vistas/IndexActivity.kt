package com.example.project_kotlin.vistas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.project_kotlin.R

class IndexActivity: AppCompatActivity(), OnClickListener{

    lateinit var cvConfig:CardView
    lateinit var cvComandas:CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.index_comandas)
        cvConfig = findViewById(R.id.cvConfiguracion)
        cvConfig.setOnClickListener({vincular()})

    }
    fun vincular(){
        var intent = Intent(this, DatosConfiguracion::class.java)
        startActivity(intent)
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}