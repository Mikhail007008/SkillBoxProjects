package org.example.warriors

import org.example.edits.Coord
import org.example.weapons.chance

class Team(val name: String) {
    val warriors: ArrayList<AbstractWarrior> = ArrayList()

    fun recruitWarrior(count: Int, position: Int) {
        for (i in 1..count) {
            val warrior = when {
                10.chance() -> General(Coord(i, position))
                40.chance() -> Captain(Coord(i, position))
                else -> Soldier(Coord(i, position))
            }
            warriors.add(warrior)
        }
    }
}