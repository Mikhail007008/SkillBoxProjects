package com.example.homework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val requestService = RequestService()
    private var currentRequestText = ""
    private val _uiState = MutableStateFlow(UiState())
    val uiState: Flow<UiState> = _uiState
    private val _requestText = MutableStateFlow("")
    val requestText: StateFlow<String> = _requestText

    fun request(searchText: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isButtonEnabled = false, isProgressVisible = true)
            }

            val isFilledField = requestService.search()

            _uiState.update {
                it.copy(isButtonEnabled = true, isProgressVisible = false)
            }

            if (isFilledField) _requestText.emit("По запросу \"$searchText\" ничего не найдено")
        }
    }

    fun requestTextEntered(searchText: String) {
        currentRequestText = searchText
        checkButtonState()
    }

    private fun checkButtonState() {
        _uiState.update {
            it.copy(isButtonEnabled = currentRequestText.trim().length > 2)
        }
    }

    data class UiState(
        val isButtonEnabled: Boolean = false,
        val isProgressVisible: Boolean = false
    )
}