package com.example.compose.data.model

interface PaginatedResponse {
    val info: PageInfo
}

data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class CharacterResponse(
    override val info: PageInfo,
    val results: List<Character>
) : PaginatedResponse

data class LocationResponse(
    override val info: PageInfo,
    val results: List<Location>
) : PaginatedResponse

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val location: CharacterLocation,
    val image: String,
    val episode: List<String>
)

data class CharacterLocation(
    val name: String,
    val url: String
)

data class Episode(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String
)

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)