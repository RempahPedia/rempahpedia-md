package com.rempahpedia.rempahpedia.data.remote.jamu

import com.google.gson.annotations.SerializedName

data class JamuDetailResponse(

	@field:SerializedName("penyakit")
	val penyakit: List<String>,

	@field:SerializedName("manfaat")
	val manfaat: List<String>,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("rempah")
	val rempah: List<String>,

	@field:SerializedName("id")
	val id: Int
)
