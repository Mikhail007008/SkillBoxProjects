package com.example.homework.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM dictionary WHERE word = :word LIMIT 1")
    suspend fun getWord(word: String): Dictionary?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Dictionary)

    @Query("DELETE FROM dictionary")
    suspend fun clearAll()

    @Query("SELECT * FROM dictionary ORDER BY count DESC LIMIT 5")
    fun getRepeatedWords(): Flow<List<Dictionary>>
}