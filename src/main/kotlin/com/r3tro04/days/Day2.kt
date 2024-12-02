package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day2 : AoCDay {
    override val day: LocalDate = day(2)

    override fun executePart1(input: String): Any {
        return input.lineSequence()
            .map { line -> line.split(" ").map(String::toInt) }
            .count { it.isSave() }
    }

    override fun executePart2(input: String): Any {
        return input.lineSequence()
            .map { line -> line.split(" ").map(String::toInt) }
            .count { sequence ->
                sequence.indices.any { index ->
                    sequence.filterIndexed { i, _ -> i != index }
                        .isSave()
                }
            }
    }

    private fun List<Int>.isSave(): Boolean {
        if (size < 2) return true

        var direction: Direction? = null

        return zipWithNext { a, b ->
            when {
                a < b -> {
                    if (direction == null) direction = Direction.ASCENDING
                    direction == Direction.ASCENDING && b - a in 1..3
                }
                a > b -> {
                    if (direction == null) direction = Direction.DESCENDING
                    direction == Direction.DESCENDING && a - b in 1..3
                }
                else -> false
            }
        }.all { it }
    }

    private enum class Direction {
        ASCENDING, DESCENDING
    }


}