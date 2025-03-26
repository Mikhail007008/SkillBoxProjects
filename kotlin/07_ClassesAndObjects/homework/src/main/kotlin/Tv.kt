package org.example

class Tv(
    brand: String,
    model: String,
    diagonalSize: String
) {
    private var tvIsOn = false
        private set
    private var channelList: List<String> = Channels.getRandomChannel()
    private var currentVolume = 0
    private var currentChannel = 0

    init {
        println(
            """
        Здравствуйте! Спасибо, что выбрали $brand $model
        С диагональю $diagonalSize дюймов
        """.trimIndent()
        )
    }

    fun turnOnTurnOffTv() {
        tvIsOn = !tvIsOn
        println("Телевизор включен - $tvIsOn")
    }

    fun turnUpVolumeBy(volume: Int) {
        if (tvIsOn) {
            currentVolume = minOf(currentVolume + volume, MAXVALUE)
            println("Громкость - $currentVolume")
        }
    }

    fun turnDownVolumeBy(volume: Int) {
        if (tvIsOn) {
            currentVolume = maxOf(currentVolume - volume, 0)
            println("Громкость - $currentVolume")
        }
    }

    fun switchChannel(channel: Int) {
        if (!tvIsOn) turnOnTurnOffTv()
        currentChannel = channel
        println("Канал - $currentChannel")
    }

    fun switchChannelUpDown(value: Int) {
        val totalChannels = channelList.size

        if (!tvIsOn) turnOnTurnOffTv()
        when {
            value > 0 -> currentChannel = (currentChannel + 1) % totalChannels
            value < 0 -> currentChannel = (currentChannel - 1 + totalChannels) % totalChannels
        }
        println("Канал - $currentChannel")
    }

    fun showAllChannels() {
        if (tvIsOn) {
            for ((i, channel) in channelList.withIndex()) println("Номер канала - ${i}, название канала - $channel")
        }
    }

    companion object {
        const val MAXVALUE = 100
    }
}