package com.example.compose.data.remote

import com.example.compose.data.model.Character
import com.example.compose.data.model.CharacterResponse
import com.example.compose.data.model.Episode
import com.example.compose.data.model.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharacterResponse

    @GET("location")
    suspend fun getLocations(@Query("page") page: Int): LocationResponse

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") characterId: Int): Character

    @GET("episode/{id}")
    suspend fun getEpisode(@Path("id") episodeId: Int): Episode
}