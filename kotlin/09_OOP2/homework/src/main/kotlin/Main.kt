package org.example

fun main() {
    val sberbankCard = Mir(0)
    sberbankCard.getBalanceInformation()
    sberbankCard.replenish(60000)
    sberbankCard.pay(50000)
    sberbankCard.getInformationAboutAvailableFunds()

    val tbankCard = Visa(1000)
    tbankCard.getBalanceInformation()
    tbankCard.replenish(50000)
    tbankCard.pay(25000)
    tbankCard.getInformationAboutAvailableFunds()
}