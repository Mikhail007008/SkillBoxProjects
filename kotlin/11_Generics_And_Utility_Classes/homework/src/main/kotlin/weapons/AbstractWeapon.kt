package org.example.weapons

import org.example.edits.MyStack

abstract class AbstractWeapon(private val maxNumbAmmoInMag: Int, private val fireType: SealedFireType) {
    private var ammoStore = MyStack<Ammo>()
    val hasAmmo: Boolean
        get() {
            return ammoStore.isEmpty()
        }

    abstract fun createAmmo(): Ammo

    fun reload() {
        for (i in 0 until maxNumbAmmoInMag) {
            ammoStore.push(createAmmo())
        }
    }

    fun getAmmoForShooting(): List<Ammo> {
        return when (fireType) {
            is SealedFireType.SingleShot -> {
                if (ammoStore.isEmpty()) {
                    listOfNotNull(ammoStore.pop())
                } else {
                    emptyList()
                }
            }

            is SealedFireType.BurstShooting -> {
                val availableAmmoCount = ammoStore.size()
                val shotsToFire = minOf(fireType.queueSize, availableAmmoCount)

                List(shotsToFire) {
                    ammoStore.pop()
                }.filterNotNull()
            }
        }
    }

}