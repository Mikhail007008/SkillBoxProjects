package org.example.weapons

import kotlin.random.Random

enum class Ammo(private val damage: Int, private val criticalChance: Int, private val criticalDamageRatio: Double) {
    PISTOLAMMO(20, 25, 2.0),
    MACHINEGUNAMMO(30, 45, 2.5),
    RIFLEAMMO(40, 55, 3.0),
    RPGAMMO(50, 35, 4.0);

    fun getCurrentDamage(): Int {
        return if (criticalChance.chance()) (damage * criticalDamageRatio).toInt()
        else damage
    }
}

fun Int.chance(): Boolean {
    return Random.nextInt(100) < this
}