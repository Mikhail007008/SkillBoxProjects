package com.example.homework

sealed class UIState {
    data object Loading : UIState()
    data class Success(val user: UserInfoModel) : UIState()
    data class Error(val message: String) : UIState()
}