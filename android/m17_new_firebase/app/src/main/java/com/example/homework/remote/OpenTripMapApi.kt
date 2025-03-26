package com.example.homework.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenTripMapApi {
    @GET("places/radius")
    suspend fun getMarkers(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("radius") radius: Int,
        @Query("rate") rate: Int,
        @Query("kinds") kinds: String,
        @Query("format") format: String,
        @Query("apikey") apikey: String
    ): List<MarkerResponse>

    @GET("places/xid/{xid}")
    suspend fun getMarkerDetails(
        @Path("xid") xid: String,
        @Query("apikey") apikey: String
    ): MarkerDetailsResponse
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.opentripmap.com/0.1/ru/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api = retrofit.create(OpenTripMapApi::class.java)
