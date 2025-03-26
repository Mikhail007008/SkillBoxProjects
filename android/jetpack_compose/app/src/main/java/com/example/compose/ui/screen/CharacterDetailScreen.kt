package com.example.compose.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.compose.R
import com.example.compose.ui.viewModel.CharacterViewModel
import com.example.compose.ui.navigation.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    characterId: Int,
    viewModel: CharacterViewModel,
    navController: NavController
) {
    val character by viewModel.characterDetail.collectAsState()
    val firstEpisode by viewModel.firstEpisode.collectAsState()

    LaunchedEffect(character, characterId) {
        viewModel.getCharacterById(characterId)
        character?.let { viewModel.fetchFirstEpisode(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.text_top_bar_detail_screen)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController as NavHostController)
        }
    ) { paddingValues ->
        character?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = rememberAsyncImagePainter(it.image),
                    contentDescription = it.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                Text(
                    text = it.name,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold
                )

                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                CharacterInfoItemWithStatus(
                    title = "Live status:",
                    value = it.status,
                    isAlive = it.status.equals(stringResource(R.string.status_character_text), ignoreCase = true)
                )

                CharacterInfoItem(
                    title = stringResource(R.string.species_and_gender_text),
                    value = "${it.species}(${it.gender})"
                )

                CharacterInfoItem(title = stringResource(R.string.last_known_location_text), value = it.location.name)

                CharacterInfoItem(
                    title = stringResource(R.string.first_seen_in_text),
                    value = firstEpisode?.name ?: stringResource(R.string.loading_text)
                )

                Text(
                    text = stringResource(R.string.episodes_text),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(6.dp)
                )

                it.episode.forEach { episodeUrl ->
                    episodeUrl.substringAfterLast("/").toIntOrNull()?.let { episodeId ->
                        EpisodeItem(episodeId = episodeId, viewModel = viewModel)
                    }
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun CharacterInfoItem(title: String, value: String) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CharacterInfoItemWithStatus(
    title: String,
    value: String,
    isAlive: Boolean
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        color = if (isAlive) Color.Green else Color.Red,
                        shape = CircleShape
                    )
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun EpisodeItem(episodeId: Int, viewModel: CharacterViewModel) {
    val episode by remember(episodeId) { viewModel.getEpisodeById(episodeId) }.collectAsState()

    episode?.let {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = it.air_date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = it.episode,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.LightGray
                    )
                }
            }
        }
    } ?: Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(24.dp))
    }
}
