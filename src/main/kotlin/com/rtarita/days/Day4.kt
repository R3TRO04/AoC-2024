package com.rtarita.days

import com.rtarita.structure.AoCDay
import com.rtarita.util.day
import kotlinx.datetime.LocalDate
import kotlin.math.max

object Day4 : AoCDay {
    private val spaceRegex = Regex("\\s+")
    private val cardRegex = Regex("Card\\s+([0-9]+):\\s.*")
    override val day: LocalDate = day(4)

    private fun String.getWinningHaving(): Pair<String, String> {
        val (winning, having) = substringAfter(':').split('|')
        return winning to having
    }

    private fun String.parseNumbers() = trim().split(spaceRegex)
        .map { it.toInt() }
        .toSet()

    private fun matching(winning: String, having: String) = (winning.parseNumbers() intersect having.parseNumbers()).size

    private fun parseCards(input: String): Map<Int, Int> = input.lineSequence()
        .associate {
            val (winning, having) = it.getWinningHaving()
            (cardRegex.matchEntire(it) ?: error("invalid line: $it")).groupValues[1].toInt() to matching(winning, having)
        }

    override fun executePart1(input: String) = input.lineSequence()
        .sumOf {
            val (winning, having) = it.getWinningHaving()
            val matching = matching(winning, having)
            max(1 shl (matching - 1), 0)
        }

    override fun executePart2(input: String): Any {
        val cards = parseCards(input).toMutableMap()
        val cardsCount = cards.keys
            .associateWith { 1 }
            .toMutableMap()

        for ((id, matching) in cards) {
            val copies = cardsCount.getValue(id)
            for (copyId in (id + 1)..(id + matching)) {
                cardsCount[copyId] = cardsCount.getValue(copyId) + copies
            }
        }

        return cardsCount.values.sum()
    }
}