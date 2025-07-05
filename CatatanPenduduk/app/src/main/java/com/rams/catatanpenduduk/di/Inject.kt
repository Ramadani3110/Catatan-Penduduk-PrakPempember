package com.rams.catatanpenduduk.di

import android.content.Context
import com.rams.catatanpenduduk.data.local.TokenManager
import com.rams.catatanpenduduk.data.remote.api.ApiConfig
import com.rams.catatanpenduduk.data.repository.CatatanPendudukRepository

object Inject {
    fun provideRepository(context: Context): CatatanPendudukRepository{
        val tokenManager = TokenManager.getInstance(context)
        val apiService = ApiConfig.getApiService(context)
        return CatatanPendudukRepository.getInstance(apiService, tokenManager)
    }
}