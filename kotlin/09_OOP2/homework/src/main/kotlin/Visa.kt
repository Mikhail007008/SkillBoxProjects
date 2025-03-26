package org.example

class Visa(balance: Int) : CreditCard(balance) {
    private var cashback: Double = 0.0
    private var savings: Double = 0.0

    override fun replenish(sum: Int) {
        if (sum >= 100000) savings += sum * 0.0001
        super.replenish(sum)
    }

    override fun pay(sum: Int): Boolean {
        if (super.pay(sum)) {
            cashbackCalc(sum)
            return true
        } else return false
    }

    private fun cashbackCalc(sum: Int) {
        if (sum > 5000) {
            cashback += sum * 0.05
        }
    }

    override fun getInformationAboutAvailableFunds() {
        println(
            """
            Кредитная карта с лимитом $maxCreditLimit. 
            Кредитные средства: $currentCreditLimit.
            Собственные средства: $balance,
            Кэшбек: $cashback,
            Накопления: $savings.
        """.trimIndent()
        )
    }
}