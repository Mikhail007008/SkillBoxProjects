package com.example.homework.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Dictionary::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao
}