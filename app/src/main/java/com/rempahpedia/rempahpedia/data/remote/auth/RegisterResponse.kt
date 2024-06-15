package com.rempahpedia.rempahpedia.data.remote.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("message")
	val message: String
)
