package org.example

class Usd : CurrencyConverter {
    override val currencyCode = "USD"
    private val crossCourse = 0.0105

    override fun convertRub(sumInRub: Double): Double {
        return sumInRub * crossCourse
    }
}