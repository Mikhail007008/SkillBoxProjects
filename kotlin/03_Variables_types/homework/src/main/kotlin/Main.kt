package org.example

fun main() {
    fun childCalculator(height: Double, weight: Float): Boolean {
        return height < 1.50 || weight < 40f
    }

    val firstName = "Mikhail"
    val lastName = "Parshin"
    var height = 1.89
    val weight  = 90f
    var isChild = childCalculator(height, weight)
    var info = """
        Моё досье
        Меня зовут: $firstName
        Фамилия: $lastName
        Рост: $height
        Вес: $weight
        Ребёнок ли я? Ответ: $isChild
    """.trimIndent()

    println(info)

    height = 1.45
    isChild = childCalculator(height, weight)
    info = """
        Моё досье
        Меня зовут: $firstName
        Фамилия: $lastName
        Рост: $height
        Вес: $weight
        Ребёнок ли я? Ответ: $isChild
    """.trimIndent()

    println(info)
}