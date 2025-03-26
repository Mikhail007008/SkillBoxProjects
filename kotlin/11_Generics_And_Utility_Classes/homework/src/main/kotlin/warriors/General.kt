package org.example.warriors

import org.example.edits.Coord
import org.example.edits.Names
import org.example.weapons.Weapons

class General(position: Coord) : AbstractWarrior(
    Names.getRandomName(),
    200,
    80,
    70,
    Weapons.createRpg(),
    position
) {

}