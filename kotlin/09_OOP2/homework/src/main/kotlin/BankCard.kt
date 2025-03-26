package org.example

abstract class BankCard(
    protected var balance: Int
) {
    abstract fun replenish(sum: Int)
    abstract fun pay(sum: Int): Boolean
    abstract fun getBalanceInformation()
    abstract fun getInformationAboutAvailableFunds()
}