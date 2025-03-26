package com.example.homework.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.homework.remote.Marker
import com.example.homework.repository.MarkersRepository
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.launch

class MapViewModel(private val repository: MarkersRepository) : ViewModel() {

    private val _userLocation = MutableLiveData<Point>()
    val userLocation: LiveData<Point> get() = _userLocation

    private val _markers = MutableLiveData<List<Marker>>()
    val markers: LiveData<List<Marker>> get() = _markers

    fun setUserLocation(lat: Double, lon: Double) {
        _userLocation.value = Point(lat, lon)
    }

    fun fetchMarkers(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val fetchedMarkers = repository.getMarkers(lat, lon)
                _markers.value = fetchedMarkers
            } catch (e: Exception) {
                Log.e("MapViewModel", "Ошибка загрузки достопримечательностей", e)
            }
        }
    }
}

class MapViewModelFactory(private val repository: MarkersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


