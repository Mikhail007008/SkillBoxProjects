package com.example.homework.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Album::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}

