package com.rams.catatanpenduduk.data.model

import com.google.gson.annotations.SerializedName

data class BaseResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)
