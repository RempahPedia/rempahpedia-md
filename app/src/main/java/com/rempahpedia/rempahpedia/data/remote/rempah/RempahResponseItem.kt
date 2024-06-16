package com.rempahpedia.rempahpedia.data.remote.rempah

import com.google.gson.annotations.SerializedName

data class RempahResponseItem(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("is_unlocked")
	val isUnlocked: Boolean,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("deskripsi")
	val deskripsi: String
)
