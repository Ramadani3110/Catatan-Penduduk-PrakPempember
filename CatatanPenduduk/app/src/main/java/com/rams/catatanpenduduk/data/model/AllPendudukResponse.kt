package com.rams.catatanpenduduk.data.model

import com.google.gson.annotations.SerializedName

data class AllPendudukResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("nik")
	val nik: String,

	@field:SerializedName("desa_id")
	val desaId: Int,

	@field:SerializedName("umur")
	val umur: Int,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("nama_desa")
	val namaDesa: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String
)
