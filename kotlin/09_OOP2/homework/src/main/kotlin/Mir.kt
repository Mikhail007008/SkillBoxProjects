package org.example

class Mir(balance: Int) : DebitCard(balance) {
    private var savings: Double = 0.0
    private var cashback: Double = 0.0

    override fun replenish(sum: Int) {
        super.replenish(sum)
        savings += sum * 0.00005
    }

    override fun pay(sum: Int): Boolean {
        if (super.pay(sum)) {
            cashback += sum * 0.01
            return true
        } else return false
    }

    override fun getInformationAboutAvailableFunds() {
        println(
            """
            Баланс: $balance,
            Кэшбек: $cashback,
            Накопления: $savings.
        """.trimIndent()
        )
    }
}