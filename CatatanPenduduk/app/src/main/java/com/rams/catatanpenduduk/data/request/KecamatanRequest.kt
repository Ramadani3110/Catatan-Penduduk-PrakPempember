package com.rams.catatanpenduduk.data.request

import com.google.gson.annotations.SerializedName

data class KecamatanRequest(

	@field:SerializedName("nama_kecamatan")
	val namaKecamatan: String
)
