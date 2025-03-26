package org.example

fun main() {
var num: Int?

do {
    println("Введите число")
    num = readlnOrNull()?.toIntOrNull()

    if (num == null || num <= 0) {
        println("Некорректный ввод. Попробуйте заново")
    }
} while (num == null || num <= 0)

println("Число Фибоначчи: ${fibonacciCalculation(num)}")
}

fun fibonacciCalculation(num: Int): Int {
    return if (num <= 1) num
    else fibonacciCalculation(num - 1) + fibonacciCalculation(num - 2)
}

