package org.example

fun main() {
    var num: Int?

    do {
        println("Укажите число вводимых номеров")
        num = readlnOrNull()?.toIntOrNull()

        if (num == null || num <= 0) {
            println("Некорректный ввод. Попробуйте заново")
        }
    } while (num == null || num <= 0)

    val numberCollection = savingNumbers(num)
    println(numberCollection.filter { it.startsWith("+7") })
    println(numberCollection.toSet().size)
    println(numberCollection.sumOf { it.toInt() })

    val modifiedCollection = numberCollection.distinct().toMutableList()
    val directory = savingNames(modifiedCollection, modifiedCollection.size)
    directory.forEach { entry -> println("Абонент: ${entry.value}. Номер телефона: ${entry.key}") }
    println(directory.toSortedMap(compareBy { it }))
}

fun savingNumbers(num: Int): MutableList<String> {
    val telephoneList: MutableList<String> = mutableListOf()
    var numberTelephone: String?

    for (i in 1..num) {
        println("Введите номер телефона:")
        numberTelephone = readlnOrNull()
        if (numberTelephone == null) println("Некорректный ввод. Попробуйте заново")
        else {
            telephoneList.add(numberTelephone)
        }
    }
    return telephoneList
}

fun savingNames(list: MutableList<String>, num: Int): MutableMap<String, String> {
    val telephoneCollection: MutableMap<String, String> = mutableMapOf()
    var firstName: String?

    for (i in 0..<num) {
        println("Введите имя человека с номером телефона ${list[i]}:")
        firstName = readlnOrNull()
        if (firstName == null) println("Некорректный ввод. Попробуйте заново")
        else {
            telephoneCollection[list[i]] = firstName
        }
    }
    return telephoneCollection
}