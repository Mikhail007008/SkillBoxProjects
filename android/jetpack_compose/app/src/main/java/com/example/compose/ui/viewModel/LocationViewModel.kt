package com.example.compose.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.compose.data.model.Location
import com.example.compose.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class LocationViewModel(repository: Repository) : ViewModel() {
    val locations: Flow<PagingData<Location>> = repository.getLocations()
        .cachedIn(viewModelScope)
}