package com.example.project_kotlin.utils

import com.example.project_kotlin.service.ApiServiceMesa

class ApiUtils {
    companion object {
        //URL con la dirección IP de cada máquina, después se coloca el de MySql subido en la nube.
        val BASE_URL="http://192.168.56.1:8091"
        fun getAPIServiceMesa(): ApiServiceMesa{
            return RetrofitClient.getClient(BASE_URL).create(ApiServiceMesa::class.java)
        }
    }
}