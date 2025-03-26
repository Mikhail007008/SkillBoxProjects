package com.example.homework

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

private const val BASE_URL = "https://randomuser.me/api/"

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val searchUsersApi: SearchUsersApi = retrofit.create(SearchUsersApi::class.java)
}

interface SearchUsersApi {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("?inc=gender,name, location, picture")
    suspend fun getUserInfo(): Response<ApiResponse>
}