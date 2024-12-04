package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day3 : AoCDay {
    override val day: LocalDate = day(3)
    private val mulRegex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
    private val controlRegex = Regex("""do\(\)|don't\(\)""")
    private val instructionRegex = Regex("""${mulRegex.pattern}|${controlRegex.pattern}""")

    override fun executePart1(input: String): Any =
        mulRegex.findAll(input)
            .sumOf { (x, y) -> x.toInt() * y.toInt() }

    override fun executePart2(input: String): Any {
        var mulEnabled = true
        var result = 0

        instructionRegex.findAll(input).forEach { matchResult ->
            val match = matchResult.value
            when {
                mulRegex.matches(match) -> if (mulEnabled) {
                    val (x, y) = mulRegex.matchEntire(match)!!.destructured
                    result += x.toInt() * y.toInt()
                }
                match == "do()" -> mulEnabled = true
                match == "don't()" -> mulEnabled = false
            }
        }
        return result
    }

    private operator fun MatchResult.component1(): String = this.destructured.component1()
    private operator fun MatchResult.component2(): String = this.destructured.component2()

}
