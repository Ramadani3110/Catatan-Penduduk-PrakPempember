package com.rams.catatanpenduduk.data.model

import com.google.gson.annotations.SerializedName

data class AllDesaResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("nama_kecamatan")
	val namaKecamatan: String,

	@field:SerializedName("nama_desa")
	val namaDesa: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("kecamatan_id")
	val kecamatanId: Int
)
