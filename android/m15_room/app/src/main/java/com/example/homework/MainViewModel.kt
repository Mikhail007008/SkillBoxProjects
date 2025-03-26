package com.example.homework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.db.App
import com.example.homework.db.Dictionary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DictionaryRepository((application as App).db.dictionaryDao())

    val repeatedWords: StateFlow<List<Dictionary>> = repository.getRepeatedWords()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun addWord(word: String) {
        if (!isValidWord(word)){
            _errorMessage.value = "Некорректный ввод"
            _errorMessage.value = null
            return
        }
        viewModelScope.launch {
            repository.addOrUpdate(word.lowercase())
            _errorMessage.value = null
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repository.clearAll()
        }
    }

    private fun isValidWord(word: String): Boolean {
        return word.matches(Regex("^[a-zA-Zа-яА-Я-]+$"))
    }
}
