package org.example.edits

import kotlin.math.pow
import kotlin.math.sqrt

class Coord(var x: Int, var y: Int) {

    fun editCoord(enemyCoord: Coord): Double {
        val distance =
            sqrt((x.toDouble() - enemyCoord.x.toDouble()).pow(2) + (y.toDouble() - enemyCoord.y.toDouble()).pow(2))
        return distance
    }

    fun findSpace(enemyCoord: Coord): Coord {
        return Coord(x - enemyCoord.x, y - enemyCoord.y)
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}