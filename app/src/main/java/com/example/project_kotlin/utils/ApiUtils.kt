package com.example.project_kotlin.utils

import com.example.project_kotlin.service.*

class ApiUtils {
    companion object {
        //URL con la dirección IP de cada máquina, después se coloca el de MySql subido en la nube.
        val BASE_URL="http://192.168.56.1:8091"

        fun getApiServiceComanda() : ApiServiceComanda{
            return RetrofitClient.getClient(BASE_URL).create(ApiServiceComanda::class.java)
        }
        fun getAPIServiceMesa(): ApiServiceMesa{
            return RetrofitClient.getClient(BASE_URL).create(ApiServiceMesa::class.java)
        }
        fun getAPIServiceEmpleado(): ApiServiceEmpleado{
            return RetrofitClient.getClient(BASE_URL).create(ApiServiceEmpleado::class.java)
        }
        fun getAPIServiceCategoriaPlato(): ApiServiceCategoriaPlato{
            return RetrofitClient.getClient(BASE_URL).create(ApiServiceCategoriaPlato::class.java)
        }

        fun getAPIServicePlato(): ApiServicePlato{
            return RetrofitClient.getClient(BASE_URL).create(ApiServicePlato::class.java)
        }
        fun getAPIServiceEstablecimiento(): ApiServiceEstablecimiento{
            return RetrofitClient.getClient(BASE_URL).create(ApiServiceEstablecimiento::class.java)
        }

    }
}