package com.example.compose.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.compose.data.model.Character
import com.example.compose.data.model.Episode
import com.example.compose.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.*

open class CharacterViewModel(private val repository: Repository) : ViewModel() {
    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.Loading)
    private val episodeCache = mutableMapOf<Int, MutableStateFlow<Episode?>>()

    open val characters: Flow<PagingData<Character>> = repository.getCharacters()
        .cachedIn(viewModelScope)
        .onStart { _loadingState.value = LoadingState.Loading }
        .catch { e -> _loadingState.value = LoadingState.Error(e.message ?: "Loading error") }
        .onEach { _loadingState.value = LoadingState.Success }

    private val _characterDetail = MutableStateFlow<Character?>(null)
    val characterDetail: StateFlow<Character?> = _characterDetail.asStateFlow()

    fun getCharacterById(characterId: Int) {
        viewModelScope.launch {
            _loadingState.value = LoadingState.Loading
            try {
                _characterDetail.value = repository.getCharacterById(characterId)
                _loadingState.value = LoadingState.Success
            } catch (e: Exception) {
                _loadingState.value = LoadingState.Error(e.message ?: "Character loading error")
            }
        }
    }

    private val _firstEpisode = MutableStateFlow<Episode?>(null)
    val firstEpisode: StateFlow<Episode?> = _firstEpisode.asStateFlow()

    fun fetchFirstEpisode(character: Character) {
        viewModelScope.launch {
            val firstEpisodeUrl = character.episode.firstOrNull()
            if (!firstEpisodeUrl.isNullOrEmpty()) {
                try {
                    _firstEpisode.value = repository.getFirstEpisode(firstEpisodeUrl)
                } catch (e: Exception) {
                    _firstEpisode.value = null
                }
            }
        }
    }

    fun getEpisodeById(episodeId: Int): StateFlow<Episode?> {
        return episodeCache.getOrPut(episodeId) {
            MutableStateFlow<Episode?>(null).also { episodeFlow ->
                viewModelScope.launch {
                    try {
                        episodeFlow.value =
                            repository.getFirstEpisode("https://rickandmortyapi.com/api/episode/$episodeId")
                    } catch (_: Exception) {
                        episodeFlow.value = null
                    }
                }
            }
        }
    }
}

sealed class LoadingState {
    object Loading : LoadingState()
    object Success : LoadingState()
    data class Error(val message: String) : LoadingState()
}