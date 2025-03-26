package org.example.battleLogic

import org.example.warriors.AbstractWarrior

interface Step {
    fun step(enemies: ArrayList<AbstractWarrior>, friends: ArrayList<AbstractWarrior>)
}