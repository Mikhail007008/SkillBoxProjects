package org.example

fun main() {
    fun programTest(tv: Tv) {
        tv.turnOnTurnOffTv()
        tv.showAllChannels()

        for (i in 0..<10) {
            tv.switchChannelUpDown(i)
        }

        for (i in 10 downTo 1) {
            tv.switchChannelUpDown(-i)
        }

        for (i in 0..<Tv.MAXVALUE) {
            tv.turnUpVolumeBy(1)
        }

        for (i in Tv.MAXVALUE downTo 1) {
            tv.turnDownVolumeBy(1)
        }

        tv.switchChannel(2)
        tv.switchChannelUpDown(-1)
        tv.switchChannelUpDown(1)
        tv.turnUpVolumeBy(20)
        tv.turnDownVolumeBy(10)
        tv.turnOnTurnOffTv()
    }

    val lgTv = Tv("Lg", "XQ-542", "55")
    programTest(lgTv)

    val samsungTv = Tv("Samsung", "US-560", "60")
    programTest(samsungTv)
}