package com.example.homework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val repository = UserRepository()
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    init {
        fetchUserInfo()
    }

    fun fetchUserInfo() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading

            try {
                val response = repository.getUserInfo()
                val user = response?.results?.firstOrNull()

                _uiState.value = if (user != null) UIState.Success(user)
                else UIState.Error("Ошибка загрузки данных")

            } catch (e: Exception) {
                _uiState.value = UIState.Error("Ошибка: ${e.localizedMessage}")
            }
        }
    }

}
