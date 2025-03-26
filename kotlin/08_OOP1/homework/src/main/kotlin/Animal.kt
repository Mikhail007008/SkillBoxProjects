package org.example

import kotlin.random.Random

open class Animal(
    var energy: Int,
    var weight: Int,
    val maxAge: Int,
    val name: String
) {
    private var currentAge = 0
    var isTooOld = currentAge >= maxAge

    private fun sleep() {
        energy += 5
        currentAge += 1
        println("$name спит")
    }

    private fun eat() {
        energy += 3
        weight += 1
        tryIncrementAge()
        println("$name ест")
    }

    protected open fun shift(): Boolean {
        return if (energy >= 5 && weight >= 1) {
            energy -= 5
            weight -= 1
            tryIncrementAge()
            println("$name передвигается")
            true
        } else {
            println("$name не хватает энергии или веса для передвижения")
            false
        }
    }

    private fun tryIncrementAge() {
        if (Random.nextBoolean() && currentAge < maxAge) currentAge += 1
        else isTooOld = true
    }

    protected open fun procreation(): Animal {
        val child = Animal(Random.nextInt(1, 10), Random.nextInt(1, 5), maxAge, name)
        println(
            "Родился $child! Его назвали $name, весом - ${this.weight}, с количеством энергии - $energy," +
                    " максимальный ввозраст составляет $maxAge"
        )
        return child
    }

    private fun performRandomAction(newBornAnimals: MutableList<Animal>) {
        when (Random.nextInt(4)) {
            0 -> eat()
            1 -> sleep()
            2 -> shift()
            3 -> {
                val child = procreation()
                newBornAnimals.add(child)
            }
        }
    }

    companion object {
        fun simulateLifeCycle(iterations: Int) {
            var count = 0
            while (count <= iterations) {
                println("\nЦикл № $count\n")
                val newBornAnimals = mutableListOf<Animal>()
                val deadAnimals = mutableListOf<Animal>()

                for (animal in NatureReserve.animalList) {
                    animal.performRandomAction(newBornAnimals)

                    if (animal.isTooOld) {
                        println("${animal.name} умер")
                        deadAnimals.add(animal)
                    }
                }

                NatureReserve.animalList.removeAll(deadAnimals)
                NatureReserve.animalList.addAll(newBornAnimals)

                if (NatureReserve.animalList.isEmpty()) {
                    println("Все животные вымерли")
                    return
                }
                count++
            }
            println("Животные, оставшиеся в живых: ${NatureReserve.animalList.size}")
        }
    }
}