package com.rams.catatanpenduduk.data.request

import com.google.gson.annotations.SerializedName

data class DesaRequest(

	@field:SerializedName("nama_desa")
	val namaDesa: String,

	@field:SerializedName("kecamatan_id")
	val kecamatanId: Int
)
