package org.example

import kotlin.random.Random

class Fish(energy: Int, weight: Int, maxAge: Int, name: String) : Animal(energy, weight, maxAge, name) {
    override fun shift(): Boolean {
        return if (super.shift()) {
            println("$name плывёт")
            true
        } else false
    }

    override fun procreation(): Fish {
        val child = Fish(Random.nextInt(1, 10), Random.nextInt(1, 5), maxAge, name)
        println(
            "Родился ${child.javaClass.simpleName}! Его назвали $name, весом - ${this.weight}, с количеством энергии - $energy," +
                    " максимальный ввозраст составляет $maxAge"
        )
        return child
    }
}