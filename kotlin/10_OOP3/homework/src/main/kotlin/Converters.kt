package org.example

object Converters {
    private val converters: List<CurrencyConverter> = listOf(Euro(), Usd())
    private val anonymous = object : CurrencyConverter {
        override val currencyCode: String = "ETC"
        override fun convertRub(sumInRub: Double): Double {
            return sumInRub * 0.0005
        }
    }

    fun get(currencyCode: String): CurrencyConverter {
        return converters.find { it.currencyCode == currencyCode } ?: anonymous
    }
}