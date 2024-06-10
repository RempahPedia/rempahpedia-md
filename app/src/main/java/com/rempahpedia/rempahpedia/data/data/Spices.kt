package com.rempahpedia.rempahpedia.data.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Spices(
    val name: String,
    val img: Int
) : Parcelable

