package com.rams.catatanpenduduk.data.repository

import com.rams.catatanpenduduk.data.remote.api.ApiService

class CatatanPendudukRepository private constructor(private val apiService: ApiService){




    companion object{
        @Volatile
        private var instance : CatatanPendudukRepository? = null
        fun getInstance(
            apiService: ApiService
        ): CatatanPendudukRepository = instance ?: synchronized(this){
            instance ?: CatatanPendudukRepository(apiService)
        }
    }

}