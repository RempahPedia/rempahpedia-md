package com.rempahpedia.rempahpedia.data.remote.api

import com.rempahpedia.rempahpedia.data.remote.jamu.JamuDetailResponse
import com.rempahpedia.rempahpedia.data.remote.jamu.JamuResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("jamu")
    suspend fun getAllJamu(): List<JamuResponseItem>

    @GET("jamu/{id}")
    suspend fun getJamuById(
        @Path("id") id: Int
    ): JamuDetailResponse

    @GET("jamu/search")
    suspend fun searchJamu(
        @Query("keyword") keyword: String,
        @Query("filter") filter: String
    ): List<JamuResponseItem>
}