package org.example.warriors

import org.example.edits.Coord
import org.example.edits.Names
import org.example.weapons.Weapons

class Captain(position: Coord) : AbstractWarrior(
    Names.getRandomName(),
    150,
    60,
    40,
    Weapons.createMachineGun(),
    position
) {
}