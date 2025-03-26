package com.example.homework.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.homework.data.Photo
import com.example.homework.data.PhotoListRepository
import com.example.homework.data.PhotoPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PhotoListViewModel private constructor(
    private val repository: PhotoListRepository
) : ViewModel() {
    constructor() : this(PhotoListRepository())

    private val earthDate = "2017-06-03"
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _photos = MutableStateFlow<List<Photo>>(emptyList())

    val pagedPhoto: Flow<PagingData<Photo>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { PhotoPagingSource(repository, earthDate) }
    ).flow.cachedIn(viewModelScope)

    init {
        loadPhotos()
    }

    private fun loadPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                _isLoading.value = true
                repository.getPhoto(earthDate, 1)
            }.fold(
                onSuccess = { _photos.value = it },
                onFailure = { Log.d("PhotoListViewModel", it.message ?: "") }
            )
            _isLoading.value = false
        }
    }

    fun refresh() {
        loadPhotos()
    }
}