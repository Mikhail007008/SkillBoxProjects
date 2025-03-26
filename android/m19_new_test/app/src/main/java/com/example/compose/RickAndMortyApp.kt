package com.example.compose

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.ui.navigation.AppNavHost
import com.example.compose.ui.navigation.BottomNavigationBar
import com.example.compose.ui.theme.AppTheme
import com.example.compose.ui.viewModel.CharacterViewModel
import com.example.compose.ui.viewModel.LocationViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RickAndMortyApp(
    characterViewModel: CharacterViewModel,
    locationViewModel: LocationViewModel
) {
    AppTheme {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) {
            AppNavHost(
                navController = navController,
                characterViewModel = characterViewModel,
                locationViewModel = locationViewModel
            )
        }
    }
}
