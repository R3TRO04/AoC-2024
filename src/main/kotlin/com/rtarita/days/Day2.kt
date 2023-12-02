package com.rtarita.days

import com.rtarita.structure.AoCDay
import com.rtarita.util.day
import kotlinx.datetime.LocalDate
import kotlin.math.max

object Day2 : AoCDay {
    private const val RED_MAX = 12
    private const val GREEN_MAX = 13
    private const val BLUE_MAX = 14
    private val gameIdRegex = Regex("Game\\s([0-9]+):.*")

    override val day: LocalDate = day(2)

    override fun executePart1(input: String) = input.lineSequence()
        .filter { line ->
            line.substringAfter(": ")
                .split(";")
                .all { group ->
                    group.split(", ")
                        .all {
                            val (numberStr, color) = it.trim().split(" ")
                            val number = numberStr.toInt()
                            when (color) {
                                "red" -> number <= RED_MAX
                                "green" -> number <= GREEN_MAX
                                "blue" -> number <= BLUE_MAX
                                else -> error("unknown color: $color")
                            }
                        }
                }
        }.sumOf {
            gameIdRegex.matchEntire(it)?.groupValues?.get(1)?.toInt() ?: error("invalid line: $it")
        }

    override fun executePart2(input: String) = input.lineSequence()
        .sumOf { line ->
            line.substringAfter(": ")
                .split("; ")
                .flatMap { group ->
                    group.split(", ")
                        .map {
                            val (number, color) = it.trim().split(" ")
                            color to number.toInt()
                        }
                }
                .groupingBy { (color, _) -> color }
                .fold({ _, elem -> elem.second }) { _, acc, elem ->
                    max(acc, elem.second)
                }
                .values
                .reduce { acc, elem -> acc * elem }
        }
}