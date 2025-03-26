import kotlinx.coroutines.*

fun main() = runBlocking {
    val players = listOf(Player("Игрок 1"), Player("Игрок 2"), Player("Игрок 3"))

    Game(players).startGame()
}