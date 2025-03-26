package org.example

open class DebitCard(balance: Int) : BankCard(balance) {

    override fun replenish(sum: Int) {
        balance += sum
        println(
            """
            После пополнения на $sum,
            Баланс карты состовляет: $balance.
        """.trimIndent()
        )
    }

    override fun pay(sum: Int): Boolean {
        if (balance >= sum) {
            balance -= sum
            println(
                """
            После оплаты на $sum,
            Баланс карты: $balance.
        """.trimIndent()
            )
        } else {
            println("Недостаточно средств для совершения операции")
            return false
        }
        return true
    }

    override fun getBalanceInformation() {
        println("Баланс: $balance")
    }

    override fun getInformationAboutAvailableFunds() {
        println("Доступные средства: $balance")
    }
}