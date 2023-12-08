package com.rtarita.days

import com.rtarita.structure.AoCDay
import com.rtarita.util.day
import kotlinx.datetime.LocalDate

object Day7 : AoCDay {
    override val day: LocalDate = day(7)

    private fun parse(input: String) = input.lineSequence()
        .map {
            val (hand, bid) = it.split(" ")
            hand to bid.toInt()
        }

    private fun typeValuePart1(hand: String): Int {
        val groups = hand.groupingBy { it }
            .eachCount()

        return when (groups.size) {
            1 -> 7
            2 -> if (groups.values.any { it == 4 }) 6 else 5
            3 -> if (groups.values.any { it == 3 }) 4 else 3
            4 -> 2
            5 -> 1
            else -> error("invalid hand with size ${hand.length}")
        }
    }

    private fun cardValuePart1(card: Char) = when (card) {
        'A' -> 14
        'K' -> 13
        'Q' -> 12
        'J' -> 11
        'T' -> 10
        in '2'..'9' -> card.digitToInt()
        else -> error("invalid card: $card")
    }

    private fun totalWinnings(
        input: String,
        typeValue: (String) -> Int,
        cardValue: (Char) -> Int
    ) = parse(input)
        .map { (hand, bid) -> Triple(typeValue(hand), hand.map { cardValue(it) }, bid) }
        .sortedWith { (typeValue1, cardValues1, _), (typeValue2, cardValues2, _) ->
            val typeComp = typeValue1 - typeValue2
            if (typeComp == 0) {
                for (i in cardValues1.indices) {
                    val cardComp = cardValues1[i] - cardValues2[i]
                    if (cardComp != 0) return@sortedWith cardComp
                }
                0
            } else typeComp
        }.foldIndexed(0) { idx, acc, (_, _, bid) ->
            acc + (idx + 1) * bid
        }

    override fun executePart1(input: String) = totalWinnings(input, ::typeValuePart1, ::cardValuePart1)

    private fun typeValuePart2(hand: String): Int {
        val jokerTarget = hand.groupingBy { it }
            .eachCount()
            .filterKeys { it != 'J' }
            .maxByOrNull { (_, groupSize) -> groupSize }
            ?.key ?: 'A'

        val replacedHand = hand.replace('J', jokerTarget)
        return typeValuePart1(replacedHand)
    }

    private fun cardValuePart2(card: Char) = when (card) {
        'A' -> 14
        'K' -> 13
        'Q' -> 12
        'T' -> 11
        in '2'..'9' -> card.digitToInt() + 1
        'J' -> 2
        else -> error("invalid card: $card")
    }

    override fun executePart2(input: String) = totalWinnings(input, ::typeValuePart2, ::cardValuePart2)
}