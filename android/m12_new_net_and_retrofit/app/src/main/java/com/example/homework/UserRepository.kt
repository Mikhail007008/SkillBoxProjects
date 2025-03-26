package com.example.homework

class UserRepository {
    suspend fun getUserInfo(): ApiResponse? {
        return try {
            val response = RetrofitInstance.searchUsersApi.getUserInfo()
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }
}

