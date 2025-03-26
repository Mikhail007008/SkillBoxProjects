package org.example.weapons

object Weapons {
    fun createPistol(): AbstractWeapon {
        return object : AbstractWeapon(10, SealedFireType.SingleShot) {
            override fun createAmmo(): Ammo {
                return Ammo.PISTOLAMMO
            }
        }
    }

    fun createMachineGun(): AbstractWeapon {
        return object : AbstractWeapon(12, SealedFireType.BurstShooting(2)) {
            override fun createAmmo(): Ammo {
                return Ammo.MACHINEGUNAMMO
            }
        }
    }

    fun createRifle(): AbstractWeapon {
        return object : AbstractWeapon(7, SealedFireType.SingleShot) {
            override fun createAmmo(): Ammo {
                return Ammo.RIFLEAMMO
            }
        }
    }

    fun createRpg(): AbstractWeapon {
        return object : AbstractWeapon(5, SealedFireType.SingleShot) {
            override fun createAmmo(): Ammo {
                return Ammo.RPGAMMO
            }
        }
    }
}