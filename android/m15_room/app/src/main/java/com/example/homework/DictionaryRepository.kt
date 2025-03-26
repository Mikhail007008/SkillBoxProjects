package com.example.homework

import com.example.homework.db.Dictionary
import com.example.homework.db.DictionaryDao
import kotlinx.coroutines.flow.Flow

class DictionaryRepository(private val dictionaryDao: DictionaryDao) {

    fun getRepeatedWords(): Flow<List<Dictionary>> = dictionaryDao.getRepeatedWords()

    suspend fun addOrUpdate(word: String) {
        val existingWord = dictionaryDao.getWord(word)
        val newCount = (existingWord?.count ?: 0) + 1
        dictionaryDao.insertWord(Dictionary(word, newCount))
    }

    suspend fun clearAll() {
        dictionaryDao.clearAll()
    }
}
