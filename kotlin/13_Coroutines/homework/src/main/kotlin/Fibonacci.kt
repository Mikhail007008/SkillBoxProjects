package org.example

import kotlinx.coroutines.*
import java.math.BigInteger

object Fibonacci {
    suspend fun take(num: Int): BigInteger {
        if (num <= 1) return num.toBigInteger()

        var a = BigInteger.ZERO
        var b = BigInteger.ONE
        var sum: BigInteger

        return try {
            withTimeout(3000) {
                for (i in 2..num) {

                    if (!currentCoroutineContext().isActive) {
                        println("Корутина отменена")
                        return@withTimeout BigInteger.ZERO
                    }

                    sum = a + b
                    a = b
                    b = sum

                    yield()
                }
                b
            }
        } catch (t: TimeoutCancellationException) {
            println("Время превысило 3 сек")
            BigInteger.ZERO
        }
    }
}