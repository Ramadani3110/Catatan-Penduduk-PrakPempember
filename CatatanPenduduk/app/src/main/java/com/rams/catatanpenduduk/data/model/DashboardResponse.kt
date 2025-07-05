package com.rams.catatanpenduduk.data.model

import com.google.gson.annotations.SerializedName

data class DashboardResponse(

	@field:SerializedName("data")
	val data: DataDashboard,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataDashboard(

	@field:SerializedName("total_kecamatan")
	val totalKecamatan: Int,

	@field:SerializedName("total_desa")
	val totalDesa: Int,

	@field:SerializedName("total_penduduk")
	val totalPenduduk: Int
)
