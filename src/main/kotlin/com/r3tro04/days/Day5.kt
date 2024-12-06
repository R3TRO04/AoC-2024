package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day5 : AoCDay {
    override val day: LocalDate = day(5)

    override fun executePart1(input: String): Any {
        val (ruleLines, dataLines) = input.lines()
            .filter(String::isNotBlank)
            .partition { "|" in it }

        val rules = ruleLines.mapNotNull { Rule.parse(it).getOrNull() }

        return dataLines.sumOf { transform(rules, it) }
    }

    override fun executePart2(input: String): Any {
        val (ruleLines, dataLines) = input.lines()
            .filter(String::isNotBlank)
            .partition { "|" in it }

        val rules = ruleLines.mapNotNull { Rule.parse(it).getOrNull() }

        val middleNumbers = dataLines.mapNotNull { line ->
            val numbers = line.split(",")
                .mapNotNull(String::toIntOrNull)

            if (!isCorrectlyOrdered(numbers, rules)) {
                reorder(numbers, rules).getOrNull(numbers.size / 2)
            } else null
        }

        return middleNumbers.sum()
    }

    private fun isCorrectlyOrdered(numbers: List<Int>, rules: List<Rule>): Boolean =
        numbers.indices.all { i ->
            (i + 1 until numbers.size).all { j ->
                rules.none { it.x == numbers[j] && it.y == numbers[i] }
            }
        }

    private fun reorder(numbers: List<Int>, rules: List<Rule>): List<Int> =
        numbers.sortedWith { a, b ->
            when {
                rules.any { it.x == a && it.y == b } -> -1
                rules.any { it.x == b && it.y == a } -> 1
                else -> 0
            }
        }

    private fun transform(rules: List<Rule>, input: String): Int {
        val data = input.split(",")
            .mapNotNull(String::toIntOrNull)

        if (data.indices.any { i ->
            (i + 1 until data.size).any { j ->
                rules.none { it.validate(data[i], data[j]) }
            }
        }) return 0

        return data.getOrNull(data.size / 2) ?: 0
    }

    data class Rule(val x: Int, val y: Int) {
        fun validate(a: Int, b: Int): Boolean = a == x && b == y

        companion object {
            fun parse(input: String): Result<Rule> = runCatching {
                input.split("|")
                    .map(String::trim)
                    .map(String::toInt)
                    .let { (x, y) -> Rule(x, y) }
            }
        }
    }
}
