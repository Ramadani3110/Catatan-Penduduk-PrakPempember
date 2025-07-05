package com.rams.catatanpenduduk.data.remote.api

import com.rams.catatanpenduduk.data.local.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token = runBlocking { tokenManager.getToken() }

        // kalo token ada, tambahin ke header authorization
        val requestWithToken = token?.let {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $it")
                .addHeader("Accept", "application/json")
                .build()
        } ?: originalRequest.newBuilder()
            .addHeader("Accept", "application/json")
            .build() // kalo gk ada return original request nya

        return chain.proceed(requestWithToken)
    }
}