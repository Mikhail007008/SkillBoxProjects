package com.example.compose.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.compose.data.model.Character
import com.example.compose.data.paging.CharacterPagingSource
import com.example.compose.data.model.Episode
import com.example.compose.data.model.Location
import com.example.compose.data.paging.LocationPagingSource
import com.example.compose.data.remote.RickAndMortyApi
import kotlinx.coroutines.flow.Flow

class Repository(private val api: RickAndMortyApi) {
    fun getCharacters(): Flow<PagingData<Character>> {
        return getPagedData { CharacterPagingSource(api) }
    }

    fun getLocations(): Flow<PagingData<Location>> {
        return getPagedData { LocationPagingSource(api) }
    }

    suspend fun getCharacterById(characterId: Int): Character? {
        return try {
            api.getCharacterById(characterId)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getFirstEpisode(episodeUrl: String): Episode {
        val episodeId = episodeUrl.substringAfterLast("/").toIntOrNull()
        return if (episodeId != null) {
            api.getEpisode(episodeId)
        } else {
            throw IllegalArgumentException("Invalid episode URL: $episodeUrl")
        }
    }
}


fun <T : Any> getPagedData(pagingSourceFactory: () -> PagingSource<Int, T>): Flow<PagingData<T>> {
    return Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = pagingSourceFactory
    ).flow
}