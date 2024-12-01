package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlin.math.abs
import kotlinx.datetime.LocalDate

object Day1 : AoCDay {
    override val day: LocalDate = day(1)

    override fun executePart1(input: String): Any {
        return parseInput(input).sumOf { (first, second) ->
            abs(first - second)
        }
    }

    override fun executePart2(input: String): Any {
        val ids = parseInput(input)
        val secondValues = ids.map { it.second }

        return ids.sumOf { (first, _) ->
            first * secondValues.count { it == first }
        }
    }

    private fun parseInput(input: String): List<Pair<Int, Int>> {
        val lines = input.lineSequence()

        val first = lines.map {
            it.substringBefore("   ").toInt()
        }.sorted()

        val second = input.lineSequence().map {
            it.substringAfter("   ").toInt()
        }.sorted()

        return first.zip(second).toList()
    }
}
