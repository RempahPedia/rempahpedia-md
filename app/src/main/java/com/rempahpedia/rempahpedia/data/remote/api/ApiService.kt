package com.rempahpedia.rempahpedia.data.remote.api

import com.rempahpedia.rempahpedia.data.remote.auth.AuthRequest
import com.rempahpedia.rempahpedia.data.remote.auth.LoginResponse
import com.rempahpedia.rempahpedia.data.remote.auth.RegisterResponse
import com.rempahpedia.rempahpedia.data.remote.jamu.JamuDetailResponse
import com.rempahpedia.rempahpedia.data.remote.jamu.JamuResponseItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    suspend fun register(
        @Body request: AuthRequest
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body request: AuthRequest
    ): LoginResponse

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