package com.rams.catatanpenduduk.data.repository

import com.rams.catatanpenduduk.data.local.TokenManager
import com.rams.catatanpenduduk.data.model.AllDesaResponse
import com.rams.catatanpenduduk.data.model.AllKecamatanResponse
import com.rams.catatanpenduduk.data.model.AllPendudukResponse
import com.rams.catatanpenduduk.data.model.BaseResponse
import com.rams.catatanpenduduk.data.model.LoginResponse
import com.rams.catatanpenduduk.data.model.SingleDesaResponse
import com.rams.catatanpenduduk.data.model.SingleKecamatanResponse
import com.rams.catatanpenduduk.data.model.SinglePendudukResponse
import com.rams.catatanpenduduk.data.remote.api.ApiService
import com.rams.catatanpenduduk.data.request.DesaRequest
import com.rams.catatanpenduduk.data.request.KecamatanRequest
import com.rams.catatanpenduduk.data.request.LoginRequest
import com.rams.catatanpenduduk.data.request.PendudukRequest
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

    // kecamatan
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
    // kecamatan

    // desa
    suspend fun getDesa(): Result<AllDesaResponse> {
        return try {
            val response = apiService.getDesa()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error("Data desa tidak ditemukan.")
                }
            } else {
                val error = response.parseError(AllDesaResponse::class.java)
                val errorMsg = error?.message ?: "Gagal mengambil data desa (${response.code()})"
                Result.Error(errorMsg)
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Terjadi kesalahan saat mengambil data desa.")
        }
    }

    suspend fun addDesa(request: DesaRequest): Result<SingleDesaResponse> {
        return try {
            val response = apiService.addDesa(request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error("Gagal menambahkan desa: Response kosong.")
                }
            } else {
                val error = response.parseError(SingleDesaResponse::class.java)
                val errorMsg = error?.message ?: "Gagal menambahkan desa (${response.code()})"
                Result.Error(errorMsg)
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Terjadi kesalahan saat menambahkan desa.")
        }
    }

    suspend fun updateDesa(id: Int, request: DesaRequest): Result<SingleDesaResponse> {
        return try {
            val response = apiService.updateDesa(id, request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error("Gagal mengubah desa: Response kosong.")
                }
            } else {
                val error = response.parseError(SingleDesaResponse::class.java)
                val errorMsg = error?.message ?: "Gagal mengubah desa (${response.code()})"
                Result.Error(errorMsg)
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Terjadi kesalahan saat mengubah desa.")
        }
    }

    suspend fun deleteDesa(id: Int): Result<BaseResponse> {
        return try {
            val response = apiService.deleteDesa(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error("Gagal menghapus desa: Response kosong.")
                }
            } else {
                val error = response.parseError(BaseResponse::class.java)
                val errorMsg = error?.message ?: "Gagal menghapus desa (${response.code()})"
                Result.Error(errorMsg)
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Terjadi kesalahan saat menghapus desa.")
        }
    }
    // desa

    // penduduk
    suspend fun getPenduduk(): Result<AllPendudukResponse> {
        return try {
            val response = apiService.getPenduduk()
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                val error = response.parseError(AllPendudukResponse::class.java)
                val errorMsg = error?.message ?: "Gagal menghapus desa (${response.code()})"
                Result.Error(errorMsg)
            }
        } catch (e: Exception) {
            Result.Error("Terjadi kesalahan: ${e.message}")
        }
    }

    suspend fun addPenduduk(request: PendudukRequest): Result<SinglePendudukResponse> {
        return try {
            val response = apiService.addPenduduk(request)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                val error = response.parseError(SinglePendudukResponse::class.java)
                val errorMsg = error?.message ?: "Gagal menghapus desa (${response.code()})"
                Result.Error(errorMsg)
            }
        } catch (e: Exception) {
            Result.Error("Terjadi kesalahan: ${e.message}")
        }
    }

    suspend fun updatePenduduk(id: Int, request: PendudukRequest): Result<SinglePendudukResponse> {
        return try {
            val response = apiService.updatePenduduk(id, request)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                val error = response.parseError(SinglePendudukResponse::class.java)
                val errorMsg = error?.message ?: "Gagal menghapus desa (${response.code()})"
                Result.Error(errorMsg)
            }
        } catch (e: Exception) {
            Result.Error("Terjadi kesalahan: ${e.message}")
        }
    }

    suspend fun deletePenduduk(id: Int): Result<BaseResponse> {
        return try {
            val response = apiService.deletePenduduk(id)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                val error = response.parseError(BaseResponse::class.java)
                val errorMsg = error?.message ?: "Gagal menghapus desa (${response.code()})"
                Result.Error(errorMsg)
            }
        } catch (e: Exception) {
            Result.Error("Terjadi kesalahan: ${e.message}")
        }
    }
    // penduduk

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