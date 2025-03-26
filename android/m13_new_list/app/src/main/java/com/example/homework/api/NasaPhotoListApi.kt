package com.example.homework.api

import com.example.homework.data.PhotoList
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaListApi {
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun photos(
        @Query("earth_date") date: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = api_key
    ): PhotoList

    private companion object {
        private const val api_key = "mayS2havGnQdqak0OYTZtjd7oxELnOfwhf4cJiq6"
    }
}

val retrofit = Retrofit
    .Builder()
    .baseUrl("https://api.nasa.gov/")
    .client(
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build()
    )
    .addConverterFactory(GsonConverterFactory.create(
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    ))
    .build()
    .create(NasaListApi::class.java)