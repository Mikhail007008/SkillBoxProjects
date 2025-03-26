package com.example.compose.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.compose.R
import com.example.compose.ui.viewModel.LocationViewModel
import com.example.compose.data.model.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(viewModel: LocationViewModel) {
    val locations = viewModel.locations.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.locations_topAppBar_text)) })
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(locations.itemCount) { index ->
                val location = locations[index]
                location?.let {
                    LocationItem(it)
                }
            }
        }
    }
}

@Composable
fun LocationItem(location: Location) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = location.name)
            Text(text = stringResource(R.string.type_text, location.type))
            Text(text = stringResource(R.string.dimension_text, location.dimension))
        }
    }
}

