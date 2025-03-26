package org.example

import kotlin.math.round
import kotlin.random.Random

fun main() {
    val wheel = Wheel()

    val randomPressure = List(3) { (round(Random.nextDouble(-1.0, 12.0) * 10)) / 10 }

    for (pressure in randomPressure) {
        try {
            println("\nПытаемся накачать колесо до $pressure атмосфер.")
            wheel.inflate(pressure)
            wheel.checkPressure()
        } catch (e: Exceptions.TooLowPressure) {
            println("Ошибка: ${e.message}")
        } catch (e: Exceptions.TooHighPressure) {
            println("Ошибка: ${e.message}")
        } catch (e: Exceptions.IncorrectPressure) {
            println("Ошибка: ${e.message}")
        }
    }
}