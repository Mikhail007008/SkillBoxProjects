package com.example.homework

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.homework.databinding.ActivityMainBinding
import com.example.homework.firebase.FirebaseHelper
import com.example.homework.map.MapController
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mapController: MapController
    private lateinit var firebaseHelper: FirebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseHelper = FirebaseHelper()

        MapKitFactory.setApiKey(getString(R.string.MAPKIT_API_KEY))
        MapKitFactory.initialize(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapController = MapController(this, binding)
        mapController.initialize()

        firebaseHelper.getToken { token ->
            Log.d("FCM", "FCM Token received: $token")
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapview.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
    }
}




