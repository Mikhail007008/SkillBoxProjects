package com.example.homework

import kotlinx.coroutines.delay

class RequestService {
    suspend fun search(): Boolean {
        delay(2000L)
        return true
    }
}