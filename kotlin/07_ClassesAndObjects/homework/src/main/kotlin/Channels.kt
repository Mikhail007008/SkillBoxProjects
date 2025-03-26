package org.example

import kotlin.random.Random

object Channels {
    private val channelList = listOf(
        "Первый", "Россия24", "ТВ-3", "НТВ",
        "ТНТ", "СТС", "Рент-ТВ", "Пятница", "Спас", "Иллюзион"
    )

    fun getRandomChannel(): List<String> {
        return channelList.shuffled().take(Random.nextInt(1, channelList.size + 1))
    }
}