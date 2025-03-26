package org.example.warriors

import org.example.edits.Coord
import org.example.edits.Names
import org.example.weapons.Weapons

class Soldier(position: Coord) : AbstractWarrior(
    Names.getRandomName(),
    100,
    50,
    30,
    Weapons.createPistol(),
    position
) {

}