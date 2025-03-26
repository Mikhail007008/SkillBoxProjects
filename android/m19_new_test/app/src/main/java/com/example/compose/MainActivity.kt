package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.compose.data.remote.RetrofitInstance
import com.example.compose.data.repository.Repository
import com.example.compose.ui.viewModel.CharacterViewModel
import com.example.compose.ui.viewModel.LocationViewModel
import com.example.compose.ui.viewModel.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository(RetrofitInstance.api)
        val viewModelFactory = ViewModelFactory(repository)
        val characterViewModel: CharacterViewModel by viewModels { viewModelFactory }
        val locationViewModel: LocationViewModel by viewModels { viewModelFactory }

        setContent {
            RickAndMortyApp(characterViewModel, locationViewModel)
        }
    }
}
