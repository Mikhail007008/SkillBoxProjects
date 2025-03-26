package com.example.compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.ui.screen.CharacterDetailScreen
import com.example.compose.ui.viewModel.CharacterViewModel
import com.example.compose.ui.screen.CharactersScreen
import com.example.compose.ui.viewModel.LocationViewModel
import com.example.compose.ui.screen.LocationsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    characterViewModel: CharacterViewModel,
    locationViewModel: LocationViewModel
) {
    NavHost(navController = navController, startDestination = "characters") {
        composable("characters") {
            CharactersScreen(viewModel = characterViewModel) { character ->
                navController.navigate("character_detail/${character.id}")
            }
        }
        composable("locations") {
            LocationsScreen(viewModel = locationViewModel)
        }
        composable("character_detail/{characterId}") { backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId")?.toIntOrNull()
            if (characterId != null) {
                CharacterDetailScreen(characterId, characterViewModel, navController)
            }
        }
    }
}


