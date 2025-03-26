package org.example.weapons

sealed class SealedFireType {
    object SingleShot : SealedFireType()
    class BurstShooting(val queueSize: Int) : SealedFireType()
}