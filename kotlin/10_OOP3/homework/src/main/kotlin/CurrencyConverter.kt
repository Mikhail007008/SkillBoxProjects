package org.example

interface CurrencyConverter {
    val currencyCode: String
    fun convertRub(sumInRub: Double): Double
}