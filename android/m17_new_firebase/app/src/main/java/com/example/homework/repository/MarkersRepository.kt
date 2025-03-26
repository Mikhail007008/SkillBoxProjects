package com.example.homework.repository

import android.util.Log
import com.example.homework.remote.Marker
import com.example.homework.remote.MarkerDetails
import com.example.homework.remote.OpenTripMapApi

class MarkersRepository(private val apiService: OpenTripMapApi) {

    suspend fun getMarkers(lat: Double, lon: Double): List<Marker> {
        return try {
            val response = apiService.getMarkers(
                lat = lat,
                lon = lon,
                radius = 10000,
                rate = 3,
                kinds = "interesting_places",
                format = "json",
                apikey = API_KEY
            )
            response.map { it.toMarker() }
        } catch (e: Exception) {
            Log.e("MarkersRepository", "Ошибка загрузки достопримечательностей", e)
            emptyList()
        }
    }

    suspend fun getMarkerDetails(xid: String): MarkerDetails? {
        return try {
            val response = apiService.getMarkerDetails(xid, API_KEY)
            MarkerDetails(
                name = response.name,
                description = response.info?.description ?: "Описание отсутствует",
                imageUrl = response.preview?.source
            )
        } catch (e: Exception) {
            Log.e("MarkersRepository", "Ошибка загрузки деталей", e)
            null
        }
    }

    companion object {
        private const val API_KEY = "5ae2e3f221c38a28845f05b61c976e90ddaeb94378c989c9319489ee"
    }
}



