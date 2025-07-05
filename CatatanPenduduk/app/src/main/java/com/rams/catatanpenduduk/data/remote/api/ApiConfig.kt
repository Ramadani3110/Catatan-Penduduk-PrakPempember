package com.rams.catatanpenduduk.data.remote.api

import android.content.Context
import com.rams.catatanpenduduk.BuildConfig
import com.rams.catatanpenduduk.data.local.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService(context: Context): ApiService{

        val tokenManager = TokenManager.getInstance(context)

        val loggingInterceptor = HttpLoggingInterceptor().setLevel(
            if(BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        )

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(TokenInterceptor(tokenManager))

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}