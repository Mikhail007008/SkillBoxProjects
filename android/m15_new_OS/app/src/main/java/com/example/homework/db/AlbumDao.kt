package com.example.homework.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    @Query("SELECT * FROM album")
    fun getAllPhotos(): Flow<List<Album>>

    @Insert
    suspend fun insertPhoto(photo: Album)
}