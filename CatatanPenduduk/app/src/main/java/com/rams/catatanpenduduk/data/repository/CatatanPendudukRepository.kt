package com.rams.catatanpenduduk.data.repository

import com.rams.catatanpenduduk.data.local.TokenManager
import com.rams.catatanpenduduk.data.model.AllKecamatanResponse
import com.rams.catatanpenduduk.data.model.BaseResponse
import com.rams.catatanpenduduk.data.model.LoginResponse
import com.rams.catatanpenduduk.data.model.SingleKecamatanResponse
import com.rams.catatanpenduduk.data.remote.api.ApiService
import com.rams.catatanpenduduk.data.request.KecamatanRequest
import com.rams.catatanpenduduk.data.request.LoginRequest
import com.rams.catatanpenduduk.helper.Result
import com.rams.catatanpenduduk.helper.parseError

class CatatanPendudukRepository private constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
){
    suspend fun login(email: String, password: String) : Result<LoginResponse>{
        return try {
            val response = apiService.login(
                LoginRequest(
                    email,
                    password
                )
            )
            if(response.isSuccessful){
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error("Data tidak ditemukan di response")
                }
            }else{
                val errorResponse = response.parseError(LoginResponse::class.java)
                val errorMessage = errorResponse?.message ?: "Login gagal (${response.code()})"
                Result.Error(errorMessage)
            }
        }catch (e: Exception){
            Result.Error(e.message ?: "Terjadi Kesalahan")
        }
    }

    suspend fun getKecamatan(): Result<AllKecamatanResponse>{
        return try {
            val response = apiService.getKecamatan()
            if(response.isSuccessful){
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error("Data tidak ditemukan di response")
                }
            }else{
                val errorResponse = response.parseError(AllKecamatanResponse::class.java)
                val errorMessage = errorResponse?.message ?: "Login gagal (${response.code()})"
                Result.Error(errorMessage)
            }
        }catch (e: Exception){
            Result.Error(e.message ?: "Terjadi Kesalahan")
        }
    }

    suspend fun addKecamatan(request: KecamatanRequest): Result<SingleKecamatanResponse> {
        return try {
            val response = apiService.addKecamatan(request)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error("Response kosong")
            } else {
                val errorResponse = response.parseError(SingleKecamatanResponse::class.java)
                val errorMessage = errorResponse?.message ?: "Login gagal (${response.code()})"
                Result.Error(errorMessage)
            }
        } catch (e: Exception) {
            Result.Error("Terjadi kesalahan: ${e.message}")
        }
    }

    suspend fun updateKecamatan(id: Int, request: KecamatanRequest): Result<SingleKecamatanResponse> {
        return try {
            val response = apiService.updateKecamatan(id, request)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error("Response kosong")
            } else {
                val errorResponse = response.parseError(SingleKecamatanResponse::class.java)
                val errorMessage = errorResponse?.message ?: "Login gagal (${response.code()})"
                Result.Error(errorMessage)
            }
        } catch (e: Exception) {
            Result.Error("Terjadi kesalahan: ${e.message}")
        }
    }

    suspend fun deleteKecamatan(id: Int): Result<BaseResponse> {
        return try {
            val response = apiService.deleteKecamatan(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error("Response kosong")
            } else {
                val errorResponse = response.parseError(BaseResponse::class.java)
                val errorMessage = errorResponse?.message ?: "Login gagal (${response.code()})"
                Result.Error(errorMessage)
            }
        } catch (e: Exception) {
            Result.Error("Terjadi kesalahan: ${e.message}")
        }
    }

    suspend fun logout() {
        tokenManager.clearToken()
    }

    suspend fun isLoggedIn(): Boolean {
        return !tokenManager.getToken().isNullOrEmpty()
    }

    companion object{
        @Volatile
        private var instance : CatatanPendudukRepository? = null
        fun getInstance(
            apiService: ApiService,
            tokenManager: TokenManager
        ): CatatanPendudukRepository = instance ?: synchronized(this){
            instance ?: CatatanPendudukRepository(apiService, tokenManager)
        }
    }

}