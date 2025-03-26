package com.example.homework.data

import com.example.homework.api.retrofit

class PhotoListRepository {
    suspend fun getPhoto(date: String, page: Int): List<Photo> {
        return retrofit.photos(date, page).photos
    }
}