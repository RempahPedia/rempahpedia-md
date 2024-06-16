package com.rempahpedia.rempahpedia.data.remote.rempah

import com.google.gson.annotations.SerializedName

data class RempahDetailResponse(

	@field:SerializedName("manfaat")
	val manfaat: List<String>,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("deskripsi")
	val deskripsi: String
)
