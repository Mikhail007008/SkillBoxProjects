package org.example

fun main() {
    val sumInRub = 500.0
    val moneyInEuro = Converters.get("EUR")
    val moneyInDollars = Converters.get("USD")
    val moneyInYuan = Converters.get("CNY")

    println("""
        $sumInRub рублей = ${moneyInEuro.convertRub(sumInRub)} ${moneyInEuro.currencyCode}
        $sumInRub рублей = ${moneyInDollars.convertRub(sumInRub)} ${moneyInDollars.currencyCode}
        $sumInRub рублей = ${moneyInYuan.convertRub(sumInRub)} ${moneyInYuan.currencyCode}
    """.trimIndent())
}