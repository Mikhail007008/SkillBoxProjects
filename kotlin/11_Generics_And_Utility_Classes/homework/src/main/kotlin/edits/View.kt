package org.example.edits

import org.example.warriors.Team
import org.example.warriors.AbstractWarrior
import java.util.*

object View {
    private var step = 1
    private val l = intArrayOf(0)
    private lateinit var firstTeam: ArrayList<AbstractWarrior>
    private lateinit var secondTeam: ArrayList<AbstractWarrior>
    private lateinit var allTeam: ArrayList<AbstractWarrior>
    private lateinit var firstTeamName: String
    private lateinit var secondTeamName: String
    private fun buildTopRow(columns: Int) =
        formatDiv("a") + (1 until columns).joinToString("") { formatDiv("-b") } + formatDiv("-c")

    private fun buildMidRow(columns: Int) =
        formatDiv("d") + (1 until columns).joinToString("") { formatDiv("-e") } + formatDiv("-f")

    private fun buildBottomRow(columns: Int) =
        formatDiv("g") + (1 until columns).joinToString("") { formatDiv("-h") } + formatDiv("-i")


    fun initialize(firstTeam: Team, secondTeam: Team) {
        View.firstTeam = firstTeam.warriors
        View.secondTeam = secondTeam.warriors
        firstTeamName = firstTeam.name
        secondTeamName = secondTeam.name
        allTeam = ArrayList(View.firstTeam + View.secondTeam)
    }

    private fun tabSetter(cnt: Int, max: Int) {
        val dif = max - cnt + 2
        if (dif > 0) System.out.printf("%" + dif + "s", ":\t") else print(":\t")
    }

    private fun formatDiv(str: String): String {
        return str.replace('a', '\u250c')
            .replace('b', '\u252c')
            .replace('c', '\u2510')
            .replace('d', '\u251c')
            .replace('e', '\u253c')
            .replace('f', '\u2524')
            .replace('g', '\u2514')
            .replace('h', '\u2534')
            .replace('i', '\u2518')
            .replace('-', '\u2500')
    }

    private fun getChar(x: Int, y: Int): String {
        var out = "| "
        for (human in allTeam) {
            if (human.position.x == x && human.position.y == y) {
                val charDisplay = human.name.first()
                out = when {
                    human.currentHealth == 0 -> "|${AnsiColors.ANSI_RED}$charDisplay${AnsiColors.ANSI_RESET}"
                    secondTeam.contains(human) -> "|${AnsiColors.ANSI_GREEN}$charDisplay${AnsiColors.ANSI_RESET}"
                    firstTeam.contains(human) -> "|${AnsiColors.ANSI_BLUE}$charDisplay${AnsiColors.ANSI_RESET}"
                    else -> out
                }
                break
            }
        }
        return out
    }

    fun view() {
        val teamSize = firstTeam.size.coerceAtMost(secondTeam.size)
        val topRow = buildTopRow(teamSize)
        val midRow = buildMidRow(teamSize)
        val bottomRow = buildBottomRow(teamSize)

        println("${AnsiColors.ANSI_RED}Step: $step${AnsiColors.ANSI_RESET}")
        step++

        allTeam.forEach { v -> l[0] = l[0].coerceAtLeast(v.toString().length) }
        print("_".repeat(l[0] * 2))
        println()

        print("$topRow    ")
        println(firstTeamName + " ".repeat(l[0] - firstTeamName.length) + ":\t$secondTeamName")

        for (i in 1..teamSize) {
            for (j in 1..teamSize) {
                print(getChar(i, j))
            }
            print("|    ")
            print(firstTeam[i - 1])
            tabSetter(firstTeam[i - 1].toString().length, l[0])
            println(secondTeam[i - 1])
            println(if (i == teamSize) bottomRow else midRow)
        }

        println("${AnsiColors.ANSI_RED}Нажмите Enter${AnsiColors.ANSI_RESET}")
    }

}