package com.rempahpedia.rempahpedia.data.remote.jamu

import com.google.gson.annotations.SerializedName

data class JamuResponseItem(

	@field:SerializedName("penyakit")
	val penyakit: List<String>,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("id")
	val id: Int
)
