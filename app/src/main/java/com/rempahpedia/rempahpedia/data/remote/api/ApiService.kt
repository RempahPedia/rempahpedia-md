package com.rempahpedia.rempahpedia.data.remote.api

import com.rempahpedia.rempahpedia.data.remote.jamu.JamuDetailResponse
import com.rempahpedia.rempahpedia.data.remote.jamu.JamuResponseItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("jamu")
    suspend fun getAllJamu(): List<JamuResponseItem>

    @GET("jamu/{id}")
    suspend fun getJamuById(@Path("id") id: Int): JamuDetailResponse
}