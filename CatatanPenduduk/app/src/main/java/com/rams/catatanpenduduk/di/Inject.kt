package com.rams.catatanpenduduk.di

import android.content.Context
import com.rams.catatanpenduduk.data.remote.api.ApiConfig
import com.rams.catatanpenduduk.data.repository.CatatanPendudukRepository

object Inject {
    fun provideRepository(context: Context): CatatanPendudukRepository{
        val apiService = ApiConfig.getApiService()
        return CatatanPendudukRepository.getInstance(apiService)
    }
}