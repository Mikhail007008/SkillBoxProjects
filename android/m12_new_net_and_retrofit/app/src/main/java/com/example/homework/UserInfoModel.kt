package com.example.homework

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoModel(
    @Json(name = "gender") val gender: String,
    @Json(name = "name") val name: NameModel,
    @Json(name = "location") val location: LocationModel,
    @Json(name = "picture") val picture: PictureModel
)

@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "results") val results: List<UserInfoModel>
)

@JsonClass(generateAdapter = true)
data class NameModel(
    @Json(name = "first") val first: String,
    @Json(name = "last") val last: String
)

@JsonClass(generateAdapter = true)
data class LocationModel(
    @Json(name = "city") val city: String,
    @Json(name = "state") val state: String,
    @Json(name = "country") val country: String
)

@JsonClass(generateAdapter = true)
data class PictureModel(
    @Json(name = "large") val large: String
)
