package com.example.homework.ui.photoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.db.Album
import com.example.homework.db.AlbumDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PhotoViewModel(private val albumDao: AlbumDao) : ViewModel() {
    val photos: Flow<List<Album>> = albumDao.getAllPhotos()

    fun addPhoto(uri: String) {
        val currentDate =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        viewModelScope.launch {
            albumDao.insertPhoto(Album(path = uri, date = currentDate))
        }
    }
}
