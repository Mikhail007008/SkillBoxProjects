package com.example.homework.map

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.homework.R
import com.example.homework.repository.LocationRepository
import com.example.homework.remote.Marker
import com.example.homework.repository.MarkersRepository
import com.example.homework.remote.api
import com.example.homework.databinding.ActivityMainBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapController(
    private val activity: FragmentActivity,
    private val binding: ActivityMainBinding
) {
    private val repository: MarkersRepository by lazy { MarkersRepository(api) }
    private val viewModel: MapViewModel by lazy {
        ViewModelProvider(activity, MapViewModelFactory(repository))[MapViewModel::class.java]
    }

    private lateinit var userLocationLayer: UserLocationLayer
    private var currentZoomLevel = 14.0f

    private val mapObjectTapListener = MapObjectTapListener { mapObject, _ ->
        val marker = mapObject.userData as? Marker
        marker?.let {
            activity.lifecycleScope.launch {
                val details = repository.getMarkerDetails(it.xid)
                withContext(Dispatchers.Main) {
                    if (details != null) {
                        val fragment = MarkerDialogBox.newInstance(
                            details.name,
                            details.description,
                            details.imageUrl
                        )
                        fragment.show(
                            activity.supportFragmentManager,
                            "marker_info_${it.xid}"
                        )
                    } else {
                        Toast.makeText(activity, "Детали отсутствуют", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        true
    }

    private val launcher = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.values.all { it }) {
            startLocation()
        } else {
            Toast.makeText(activity, "Разрешение на местоположение отклонено", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun initialize() {
        userLocationLayer =
            MapKitFactory.getInstance().createUserLocationLayer(binding.mapview.mapWindow)

        viewModel.userLocation.observe(activity) { location ->
            updateUserLocation(location)
        }
        viewModel.markers.observe(activity) { markers ->
            displayMarkers(markers)
        }

        setupListeners()
        checkPermissions()
    }

    private fun setupListeners() {
        binding.currentLocationButton.setOnClickListener {
            val target = userLocationLayer.cameraPosition()?.target
            if (target != null) {
                binding.mapview.mapWindow.map.move(
                    CameraPosition(target, 14.0f, 0.0f, 0.0f),
                    Animation(Animation.Type.SMOOTH, 1f), null
                )
            } else {
                Toast.makeText(activity, "Не удалось получить местоположение", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.zoomIn.setOnClickListener { zoomIn() }
        binding.zoomOut.setOnClickListener { zoomOut() }
    }

    private fun updateMapZoom() {
        binding.mapview.map.move(
            CameraPosition(binding.mapview.map.cameraPosition.target, currentZoomLevel, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 1f), null
        )
    }

    private fun updateUserLocation(location: Point) {
        binding.mapview.mapWindow.map.move(
            CameraPosition(location, 15.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 1f), null
        )
    }

    private fun displayMarkers(markers: List<Marker>) {
        val mapObjects = binding.mapview.map.mapObjects
        mapObjects.clear()

        markers.forEach { marker ->
            val placemark = mapObjects.addPlacemark(marker.point)
            placemark.userData = marker
            placemark.setIcon(
                ImageProvider.fromResource(activity, R.drawable.marker)
            )
            placemark.addTapListener(mapObjectTapListener)
        }
    }

    private fun checkPermissions() {
        val required = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET
        )
        if (required.all { permission ->
                ContextCompat.checkSelfPermission(
                    activity,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            startLocation()
        } else {
            launcher.launch(required)
        }
    }

    private fun startLocation() {
        val locationRepository = LocationRepository(activity)

        locationRepository.getUserLocation(
            onSuccess = { userPoint ->
                viewModel.setUserLocation(userPoint.latitude, userPoint.longitude)
                updateUserLocation(userPoint)
                enableUserLocation()
                viewModel.fetchMarkers(userPoint.latitude, userPoint.longitude)
            },
            onFailure = { error ->
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun enableUserLocation() {
        userLocationLayer.isVisible = true
        userLocationLayer.isHeadingEnabled = true
    }

    private fun zoomIn() {
        if (currentZoomLevel < 17.0f) {
            currentZoomLevel += 1.0f
            updateMapZoom()
        }
    }

    private fun zoomOut() {
        if (currentZoomLevel > 5.0f) {
            currentZoomLevel -= 1.0f
            updateMapZoom()
        }
    }
}
