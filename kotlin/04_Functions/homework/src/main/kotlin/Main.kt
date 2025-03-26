package org.example

fun main() {
    val source = "F2p)v\"y233{0->c}ttelciFc"
    val firstHalfText = source.substring(0, source.length / 2)
    val secondHalfText = source.substring(source.length / 2)
    val decodedFirstHalfText = decodingFirstHalf(firstHalfText)
    val decodedSecondHalfText = decodingSecondHalf(secondHalfText)

    println("$decodedFirstHalfText$decodedSecondHalfText")
}

fun decodingFirstHalf(text: String): String {
    var result = text
    result = characterShift(result, numbToShift(1))
    result = result.replace('5', 's')
    result = result.replace('4', 'u')
    result = characterShift(result, numbToShift(-3))
    result = result.replace('0', 'o')

    return  result
}

fun decodingSecondHalf(text: String): String {
    var result = text
    result = result.reversed()
    result = characterShift(result, numbToShift(-4))
    result = result.replace('_', ' ')

    return  result
}

fun characterShift(input: String, transformation: (Char) -> Char): String {
    return input.map(transformation).joinToString("")
}

fun numbToShift(amount: Int): (Char) -> Char {
    return { c -> (c + amount) }
}
