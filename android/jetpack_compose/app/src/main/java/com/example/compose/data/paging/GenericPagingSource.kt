package com.example.compose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.compose.data.model.Character
import com.example.compose.data.model.CharacterResponse
import com.example.compose.data.model.Location
import com.example.compose.data.model.LocationResponse
import com.example.compose.data.model.PaginatedResponse
import com.example.compose.data.remote.RickAndMortyApi

open class GenericPagingSource<T : Any, TResponse : PaginatedResponse>(
    private val apiCall: suspend (Int) -> TResponse,
    private val extractResults: (TResponse) -> List<T>
) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: 1
            val response = apiCall(page)

            LoadResult.Page(
                data = extractResults(response),
                prevKey = if (page == 1) null else page - 1,
                nextKey = response.info.next?.let { page + 1 }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}

class CharacterPagingSource(api: RickAndMortyApi) :
    GenericPagingSource<Character, CharacterResponse>(
        apiCall = { page -> api.getCharacters(page) },
        extractResults = { response -> response.results }
    )

class LocationPagingSource(api: RickAndMortyApi) :
    GenericPagingSource<Location, LocationResponse>(
        apiCall = { page -> api.getLocations(page) },
        extractResults = { response -> response.results }
    )
