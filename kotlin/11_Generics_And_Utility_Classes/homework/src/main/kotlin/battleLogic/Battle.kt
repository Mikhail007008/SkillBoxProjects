package org.example.battleLogic

import org.example.edits.View
import org.example.warriors.Team
import java.util.*

class Battle(private val team1: Team, private val team2: Team) {
    private var steps = 0
    private val maxSteps = 30

    fun startBattle(): BattleState {
        val scanner = Scanner(System.`in`)
        while (steps < maxSteps) {
            View.view()

            if (scanner.hasNextLine()) {
                scanner.nextLine()
            }

            for (warrior in team1.warriors) {
                warrior.step(team2.warriors, team1.warriors)
            }
            for (warrior in team2.warriors) {
                warrior.step(team1.warriors, team2.warriors)
            }

            val sum1Hp = team1.warriors.sumOf { it.currentHealth }
            val sum2Hp = team2.warriors.sumOf { it.currentHealth }

            if (sum1Hp == 0) {
                return BattleState.Team2Wins
            } else if (sum2Hp == 0) {
                return BattleState.Team1Wins
            }

            steps++

            return BattleState.Progress(sum1Hp, sum2Hp)
        }

        val team1Alive = team1.warriors.count { it.currentHealth > 0 }
        val team2Alive = team2.warriors.count { it.currentHealth > 0 }

        return when {
            team1Alive > team2Alive -> BattleState.Team1Wins
            team2Alive > team1Alive -> BattleState.Team2Wins
            else -> BattleState.Draw
        }
    }
}



