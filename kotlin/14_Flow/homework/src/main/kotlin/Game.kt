import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class Game(private val players: List<Player>) {
    private val numberFlow = MutableSharedFlow<Int>(replay = 0)
    private val gameStateFlow = MutableStateFlow(false)

    suspend fun startGame() = coroutineScope {
        val leadJob = launch { lead() }

        val playerJobs = players.map { player ->
            launch { play(player) }
        }

        leadJob.join()
        playerJobs.forEach { it.cancelAndJoin() }
        println("Игра завершена.")
    }

    private suspend fun lead() {
        while (gameStateFlow.value.not()) {
            val number = BarrelGenerator.getNextNumber() ?: break
            println("Ведущий объявляет номер: $number")
            numberFlow.emit(number)
            delay(500)
        }
    }

    private suspend fun play(player: Player) {
        numberFlow.collect { number ->
            if (gameStateFlow.value) return@collect
            println("${player.name} проверяет номер $number")
            if (player.markNumber(number)) {
                println("${player.name} закрывает строку и выигрывает!")
                gameStateFlow.value = true
            }
        }
    }
}