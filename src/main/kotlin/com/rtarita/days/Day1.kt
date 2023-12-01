package com.rtarita.days

import com.rtarita.structure.AoCDay
import com.rtarita.util.day
import kotlinx.datetime.LocalDate

object Day1 : AoCDay {
    override val day: LocalDate = day(1)

    private enum class Digits(val spelling: String, val value: Int) {
        ONE("one", 1),
        TWO("two", 2),
        THREE("three", 3),
        FOUR("four", 4),
        FIVE("five", 5),
        SIX("six", 6),
        SEVEN("seven", 7),
        EIGHT("eight", 8),
        NINE("nine", 9);

        companion object {
            fun parse(spelling: String): Digits = entries.first { it.spelling == spelling }
            val spellings = entries.map { it.spelling }
        }
    }

    private fun String.parse(): Digits = Digits.parse(this)

    override fun executePart1(input: String): Any {
        return input.lineSequence()
            .sumOf { line ->
                val digits = line.filter { it.isDigit() }
                    .map { it.digitToInt() }
                digits.first() * 10 + digits.last()
            }
    }

    override fun executePart2(input: String): Any {
        return input.lineSequence()
            .sumOf { line ->
                val firstSpelling = line.findAnyOf(Digits.spellings)
                val lastSpelling = line.findLastAnyOf(Digits.spellings)
                val firstDigit = line.indexOfFirst { it.isDigit() }
                val lastDigit = line.indexOfLast { it.isDigit() }

                val first = if (firstSpelling != null && (firstDigit == -1 || firstSpelling.first < firstDigit)) {
                    firstSpelling.second.parse().value
                } else {
                    line[firstDigit].digitToInt()
                }
                val last = if (lastSpelling != null && (lastDigit == -1 || lastSpelling.first > lastDigit)) {
                    lastSpelling.second.parse().value
                } else {
                    line[lastDigit].digitToInt()
                }
                first * 10 + last
            }
    }
}