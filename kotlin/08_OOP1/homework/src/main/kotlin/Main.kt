package org.example

fun main() {
    val poodle = Dog(7, 10, 15, "Sharik")
    val jackRussell = Dog(8, 7, 16, "Mailo")
    val perch = Fish(2, 1, 3, "Johny")
    val som = Fish(1, 4, 4, "Olga")
    val carp = Fish(2, 2, 2, "Suzy")
    val sparrow = Bird(10, 1, 1, "Jack")
    val pigeon = Bird(8, 2, 2, "Leo")
    val parrot = Bird(5, 2, 5, "Carl")
    val flamingo = Bird(4, 4, 6, "Cleopatra")
    val eagle = Bird(6, 4, 5, "Sam")

    NatureReserve.animalList.addAll(
        listOf(
            poodle, jackRussell, perch,
            som, carp, sparrow, pigeon, parrot, flamingo, eagle
        )
    )

    Animal.simulateLifeCycle(5)

}