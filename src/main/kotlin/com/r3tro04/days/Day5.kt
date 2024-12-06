package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day5 : AoCDay {
    override val day: LocalDate = day(5)

    override fun executePart1(input: String): Any {
        val (ruleLines, dataLines) = input.lines().partition { line ->
            line.isNotBlank() && "|" in line
        }
        val rules = ruleLines.mapNotNull { line ->
            Rule.parse(line).getOrNull()
        }

        return dataLines.asSequence()
            .filter { it.isNotBlank() }
            .sumOf { line -> transform(rules, line) }
    }

    override fun executePart2(input: String): Any {
        return "not implemented"
    }

    private fun transform(rules: List<Rule>, input: String): Int {
        val data = input.split(",")
            .mapNotNull { it.toIntOrNull() }

        for (i in data.indices) {
            for (j in i + 1 until data.size) {
                if (rules.none { it.validate(data[i], data[j]) }) return 0
            }
        }

        return data[data.size / 2]
    }

    data class Rule(val x: Int, val y: Int) {
        fun validate(a: Int, b: Int): Boolean = a == x && b == y

        companion object {
            fun parse(input: String): Result<Rule> = runCatching {
                val (x, y) = input.split("|").map { it.trim().toInt() }
                Rule(x, y)
            }
        }
    }
}