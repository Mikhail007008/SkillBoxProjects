package org.example

import org.example.battleLogic.Battle
import org.example.battleLogic.BattleState
import org.example.edits.View
import org.example.warriors.Team

fun main() {
    val holyTeam = Team("HollyTeam")
    val darkTeam = Team("DarkTeam")

    holyTeam.recruitWarrior(10, 1)
    darkTeam.recruitWarrior(10, 10)

    View.initialize(holyTeam, darkTeam)

    val battle = Battle(holyTeam, darkTeam)
    while (true) {
        when (val result = battle.startBattle()) {
            is BattleState.Team1Wins -> {
                println("Победила команда ${holyTeam.name}")
                break
            }

            is BattleState.Team2Wins -> {
                println("Победила команда ${darkTeam.name}")
                break
            }

            is BattleState.Draw -> {
                println("Ничья!")
                break
            }

            is BattleState.Progress -> {
                println("\nБитва продолжается: ${result.team1Health} vs ${result.team2Health}")
            }
        }
    }
}

