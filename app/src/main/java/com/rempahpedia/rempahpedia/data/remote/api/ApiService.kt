package com.rempahpedia.rempahpedia.data.remote.api

import com.rempahpedia.rempahpedia.data.remote.jamu.JamuResponseItem
import retrofit2.http.GET

interface ApiService {
    @GET("jamu")
    suspend fun getJamu(): List<JamuResponseItem>
}