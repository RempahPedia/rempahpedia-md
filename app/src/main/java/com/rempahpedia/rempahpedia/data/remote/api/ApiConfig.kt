package com.rempahpedia.rempahpedia.data.remote.api

import com.rempahpedia.rempahpedia.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService(): ApiService {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)

        val client = clientBuilder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://rempahpedia-6qjjxs4fia-et.a.run.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)

    }
}