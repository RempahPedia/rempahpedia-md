package com.rempahpedia.rempahpedia.data.remote.api

import com.rempahpedia.rempahpedia.data.remote.auth.AuthRequest
import com.rempahpedia.rempahpedia.data.remote.auth.LoginResponse
import com.rempahpedia.rempahpedia.data.remote.auth.RegisterResponse
import com.rempahpedia.rempahpedia.data.remote.jamu.JamuDetailResponse
import com.rempahpedia.rempahpedia.data.remote.jamu.JamuResponseItem
import com.rempahpedia.rempahpedia.data.remote.prediction.PredictionRequest
import com.rempahpedia.rempahpedia.data.remote.rempah.RempahDetailResponse
import com.rempahpedia.rempahpedia.data.remote.rempah.RempahResponseItem
import com.rempahpedia.rempahpedia.data.remote.rempah.RempahUnlockedResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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

    @POST("prediction/save")
    suspend fun savePrediction(
        @Header("Cookie") token: String,
        @Body request: PredictionRequest
    ): RegisterResponse

    @GET("rempah")
    suspend fun getAllRempah(
        @Header("Cookie") token: String
    ): List<RempahResponseItem>

    @GET("prediction/unlocked")
    suspend fun getRempahUnlocked(
        @Header("Cookie") token: String
    ): RempahUnlockedResponse

    @GET("rempah/{id}")
    suspend fun getRempahById(
        @Path("id") id: Int
    ): RempahDetailResponse

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