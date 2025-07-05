package com.rams.catatanpenduduk.helper

import com.google.gson.Gson
import retrofit2.Response

fun <T> Response<T>.parseError(clazz: Class<T>): T?{
    return try {
        val errorBody = this.errorBody()?.string()
        if (errorBody != null) {
            val error = Gson().fromJson(errorBody, clazz)
            error
        } else {
            null
        }
    }catch (e: Exception){
        null
    }
}