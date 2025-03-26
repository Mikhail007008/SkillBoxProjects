package com.example.homework.remote

import com.google.gson.annotations.SerializedName
import com.yandex.mapkit.geometry.Point

data class MarkerResponse(
    val xid: String,
    val name: String,
    val point: PointResponse,
    val kinds: String?,
    val rate: Int?,
    @SerializedName("preview") val preview: ImageResponse?
) {
    fun toMarker() = Marker(
        xid = xid,
        name = name,
        point = Point(point.lat, point.lon),
        imageUrl = preview?.source
    )
}

data class PointResponse(
    val lat: Double,
    val lon: Double
)

data class ImageResponse(
    val source: String
)

data class Marker(
    val xid: String,
    val name: String,
    val point: Point,
    val imageUrl: String? = null
)

data class MarkerDetailsResponse(
    val xid: String,
    val name: String,
    @SerializedName("info") val info: InfoResponse?,
    @SerializedName("preview") val preview: ImageResponse?
)

data class InfoResponse(
    @SerializedName("descr") val description: String?
)

data class MarkerDetails(
    val name: String,
    val description: String,
    val imageUrl: String?
)




