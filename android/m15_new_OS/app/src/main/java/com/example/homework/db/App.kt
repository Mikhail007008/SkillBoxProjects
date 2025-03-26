package com.example.homework.db

import android.app.Application
import androidx.room.Room

class App : Application() {
    val db: AppDataBase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "photo_database"
        ).build()
    }
}