package org.example.warriors

import org.example.weapons.AbstractWeapon
import org.example.edits.Coord
import org.example.battleLogic.Step
import kotlin.math.abs

abstract class AbstractWarrior(
    val name: String,
    maxHealth: Int,
    override val agility: Int,
    private val accuracy: Int,
    private val weapon: AbstractWeapon,
    var position: Coord
) : Warrior, Step {
    var currentHealth = maxHealth
    override var isKilled: Boolean = false
        get() = currentHealth <= 0


    override fun attack(opponent: Warrior) {
        when {
            weapon.hasAmmo -> {
                weapon.reload()
                return
            }

            isKilled -> return
        }

        val ammoForShooting = weapon.getAmmoForShooting()
        var totalDamage = 0

        for (ammo in ammoForShooting) {
            val isHit = (1..100).random() <= accuracy && (1..100).random() > opponent.agility
            if (isHit) {
                val damage = ammo.getCurrentDamage()
                totalDamage += damage
                println("$name попал в $opponent, урон: $damage")
            } else {
                println("$name промахнулся по $opponent")
            }
        }

        if (totalDamage > 0) {
            opponent.takeDamage(totalDamage)
        }
    }

    override fun takeDamage(damage: Int) {
        currentHealth -= damage
        if (currentHealth <= 0) {
            currentHealth = 0
            isKilled = true
            println("$name покинул нас")
        }
    }

    private fun findEnemy(enemies: ArrayList<AbstractWarrior>): AbstractWarrior? {
        if (enemies.isEmpty()) return null

        var closestEnemy: AbstractWarrior? = null
        var minDist = Double.MAX_VALUE

        for (enemy in enemies) {
            val dist = position.editCoord(enemy.position)
            if (dist < minDist && enemy.currentHealth > 0) {
                minDist = dist
                closestEnemy = enemy
            }
        }
        return closestEnemy
    }


    override fun step(enemies: ArrayList<AbstractWarrior>, friends: ArrayList<AbstractWarrior>) {
        if (currentHealth <= 0) return

        val target = findEnemy(enemies) ?: return

        if (position.editCoord(target.position) < 5) {
            attack(target)
            return
        }

        val space = position.findSpace(target.position)
        val newCoord = Coord(position.x, position.y)

        if (abs(space.x) > abs(space.y)) {
            newCoord.x += if (space.x < 0) 1 else -1
        } else {
            newCoord.y += if (space.y < 0) 1 else -1
        }

        for (friend in friends) {
            if (friend.position == newCoord && friend.currentHealth > 0) {
                return
            }
        }

        position = newCoord
    }

    override fun toString(): String {
        return "$name ♥: $currentHealth"
    }
}