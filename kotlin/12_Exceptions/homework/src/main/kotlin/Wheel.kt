package org.example

class Wheel {
    private var pressure: Double = 0.0

    fun inflate(pressure: Double) {
        when {
            pressure < 0 -> throw Exceptions.IncorrectPressure("Давление не может быть отрицательным.")
            pressure > 10 -> throw Exceptions.IncorrectPressure("Давление не может превышать 10 атмосфер, колесо может взорваться!")
            pressure < 1.6 -> throw Exceptions.TooLowPressure("Давление слишком низкое. Эксплуатация невозможна.")
            pressure > 2.5 -> throw Exceptions.TooHighPressure("Давление слишком высокое. Эксплуатация невозможна.")
            else -> {
                this.pressure = pressure
                println("Колесо успешно накачано до $pressure атмосфер.")
            }
        }
    }

    fun checkPressure() {
        println("Давление в норме: $pressure атмосфер. Колесо готово к эксплуатации.")
    }
}