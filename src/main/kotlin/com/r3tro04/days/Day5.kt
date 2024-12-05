package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day5 : AoCDay {
    override val day: LocalDate = day(5)

    override fun executePart1(input: String): Any {
        val separator = input.lines().indexOf("")
        val rules = input.lines().filterIndexed { index, _ ->
            index < separator
        }.map {
            Rule.parse(it)
        }
        val data = input.lines().filterIndexed { index, _ ->
            index > separator
        }
        return data.sumOf { s ->
            transform(rules, s)
        }
    }

    override fun executePart2(input: String): Any {
        return "(not implemented)"
    }


    private fun transform(rules: List<Rule>, input: String): Int {
        val data = input.lines().map { it.split(",") }
            .map{ it.map(String::toInt) }

        return buildList {
            data.forEach { list ->
                add(
                    buildList {
                        list.forEachIndexed { index, a ->
                            list.forEach { b ->
                                if (a != b)
                                    if (rules.filter { it.x == a }.any { it.validate(a,b) })
                                        add(list[index])
                            }
                        }
                    }
                )
            }
        }.sumOf {
            if (it.isNotEmpty()) it[(it.size / 2)]
            else 0
        }
    }

    data class Rule(val x: Int, val y: Int) {
        fun validate(a: Int, b: Int): Boolean {
            return a == x && b == y
        }
        companion object {
            fun parse(str: String): Rule =
                Rule(
                    str.substringBefore("|").toInt(),
                    str.substringAfter("|").toInt(),
                )
        }
    }
}