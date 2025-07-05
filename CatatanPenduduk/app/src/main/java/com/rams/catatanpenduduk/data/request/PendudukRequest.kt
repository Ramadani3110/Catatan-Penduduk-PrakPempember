package com.rams.catatanpenduduk.data.request

import com.google.gson.annotations.SerializedName

data class PendudukRequest(

	@field:SerializedName("nik")
	val nik: String,

	@field:SerializedName("desa_id")
	val desaId: Int,

	@field:SerializedName("umur")
	val umur: Int,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String
)
