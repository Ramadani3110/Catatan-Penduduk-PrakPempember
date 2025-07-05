package com.rams.catatanpenduduk.data.remote.api

import com.rams.catatanpenduduk.data.model.AllKecamatanResponse
import com.rams.catatanpenduduk.data.model.AllPendudukResponse
import com.rams.catatanpenduduk.data.model.BaseResponse
import com.rams.catatanpenduduk.data.model.DashboardResponse
import com.rams.catatanpenduduk.data.model.LoginResponse
import com.rams.catatanpenduduk.data.model.SingleKecamatanResponse
import com.rams.catatanpenduduk.data.request.KecamatanRequest
import com.rams.catatanpenduduk.data.request.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("api/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @GET("api/dashboard")
    suspend fun dashboard(): Response<DashboardResponse>

    // kecamatans start
    @GET("api/kecamatans")
    suspend fun getKecamatan(): Response<AllKecamatanResponse>

    @POST("api/kecamatans")
    suspend fun addKecamatan(
        @Body kecamatanRequest: KecamatanRequest
    ): Response<SingleKecamatanResponse>

    @POST("api/kecamatans/{id}")
    suspend fun updateKecamatan(
        @Path("id") id: Int,
        @Body kecamatanRequest: KecamatanRequest
    ): Response<SingleKecamatanResponse>

    @GET("api/kecamatans/{id}")
    suspend fun getKecamatanById(
        @Path("id") id: Int
    ): Response<SingleKecamatanResponse>

    @DELETE("api/kecamatans/{id}")
    suspend fun deleteKecamatan(
        @Path("id") id: Int
    ): Response<BaseResponse>
    // kecamatans end

}