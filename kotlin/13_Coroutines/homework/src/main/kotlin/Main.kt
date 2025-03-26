package org.example

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val job1 = launch {
            while (isActive) {
                print(".")
                delay(100)
            }
        }

        val job2 = launch {
            println("${measureTimeMillis { println(Fibonacci.take(5000)) }} ms")
        }

        val job3 = launch {
            println("${measureTimeMillis { println(Fibonacci.take(100000)) }} ms")
        }

        val job4 = launch {
            println("${measureTimeMillis { println(Fibonacci.take(500000)) }} ms")
        }

        job2.join()
        job3.join()
        job4.join()

        job1.cancelAndJoin()
    }
}