package org.example

open class CreditCard(balance: Int) : BankCard(balance) {
    protected var currentCreditLimit = 30000
    protected var maxCreditLimit = 30000

    override fun replenish(sum: Int) {
        if (currentCreditLimit < maxCreditLimit) {
            val count = minOf(sum, maxCreditLimit - currentCreditLimit)
            currentCreditLimit += count
            val residue = sum - count
            balance += residue
        } else balance += sum

        println(
            """
            После пополнения на $sum,
            Кредитные средства: $currentCreditLimit,
            Собственные средства: $balance.
        """.trimIndent()
        )
    }

    override fun pay(sum: Int): Boolean {
        if (balance >= sum) balance -= sum
        else {
            val count = sum - balance

            if (currentCreditLimit >= count) {
                currentCreditLimit -= count
                balance = 0
            } else {
                println("Недостаточно средств для совершения операции")
                return false
            }
        }
        println(
            """
            После оплаты на $sum,
            Кредитные средства: $currentCreditLimit,
            Собственные средства: $balance.
        """.trimIndent()
        )
        return true
    }

    override fun getBalanceInformation() {
        println("Баланс: $balance")
    }

    override fun getInformationAboutAvailableFunds() {
        println(
            """
            Кредитная карта с лимитом $maxCreditLimit. 
            Кредитные средства: $currentCreditLimit.
            Собственные средства: $balance. 
        """.trimIndent()
        )
    }

}