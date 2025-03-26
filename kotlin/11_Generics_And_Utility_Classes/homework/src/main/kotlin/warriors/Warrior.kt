package org.example.warriors

interface Warrior {
    val isKilled: Boolean
    val agility: Int

    fun attack(opponent: Warrior)
    fun takeDamage(damage: Int)
}