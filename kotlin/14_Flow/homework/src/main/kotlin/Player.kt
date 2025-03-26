class Player(val name: String) {
    private val card = generateCard()
    private val markedRows = MutableList(3) { false }

    init {
        println("$name получил карточку:\n")
        printCard()
    }

    private fun generateCard(): List<MutableList<Int?>> {
        val uniqueNumbers = (1..90).shuffled().take(15)

        return uniqueNumbers.chunked(5).map { row ->
            (row + List(4) { null }).shuffled().toMutableList()
        }
    }

    private fun printCard() {
        card.forEach { row ->
            println(row.joinToString(" ") { it?.toString() ?: "_" })
            println()
        }
    }

    fun markNumber(number: Int): Boolean {
        card.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, cell ->
                if (cell == number) {
                    row[colIndex] = null
                    if (row.all { it == null }) markedRows[rowIndex] = true
                    return markedRows[rowIndex]
                }
            }
        }
        return false
    }
}
