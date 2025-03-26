package org.example

class Euro : CurrencyConverter {
    override val currencyCode = "EUR"
    private val crossCourse = 0.0096

    override fun convertRub(sumInRub: Double): Double {
        return sumInRub * crossCourse
    }
}