object BarrelGenerator {
    private val numbers = (1..90).shuffled().toMutableList()
    private var currentIndex = 0

    fun getNextNumber(): Int? = if (currentIndex < numbers.size) numbers[currentIndex++] else null
}