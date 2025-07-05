package com.rams.catatanpenduduk.data.model

import com.google.gson.annotations.SerializedName

data class SingleDesaResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("nama_desa")
	val namaDesa: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("kecamatan_id")
	val kecamatanId: Int
)
