package com.rams.catatanpenduduk.data.model

import com.google.gson.annotations.SerializedName

data class AllKecamatanResponse(

	@field:SerializedName("data")
	val data: List<DataKecamatan>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataKecamatan(

	@field:SerializedName("nama_kecamatan")
	val namaKecamatan: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int
)
