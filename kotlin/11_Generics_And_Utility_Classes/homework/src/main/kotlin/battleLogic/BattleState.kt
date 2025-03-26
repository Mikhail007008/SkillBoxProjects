package org.example.battleLogic

sealed class BattleState {
    data class Progress(val team1Health: Int, val team2Health: Int) : BattleState()
    object Team1Wins : BattleState()
    object Team2Wins : BattleState()
    object Draw : BattleState()
}