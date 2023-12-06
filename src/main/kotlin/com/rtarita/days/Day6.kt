package com.rtarita.days

import com.rtarita.structure.AoCDay
import com.rtarita.util.day
import com.rtarita.util.sqr
import kotlinx.datetime.LocalDate
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.math.sqrt

/**
 * The distances resulting in holding down the button for n milliseconds follow a parabola defined as
 * `-square(n) + time * n`. The current record distance in this mathematical representation is a
 * line that is parallel to the n-Axis. The equation `-square(n) + time * n == recordDist` yields
 * two solutions, which are precisely the lower and upper bound of the possible solutions. The equation
 * can be solved using the quadratic formula.
 */
object Day6 : AoCDay {
    private val SPACE_REGEX = Regex("\\s+")
    override val day: LocalDate = day(6)

    private fun parseInput(input: String): Map<Long, Long> {
        val (time, distance) = input.lines()
            .map { line ->
                line.trimStart { !it.isDigit() }
                    .split(SPACE_REGEX)
                    .map { it.toLong() }
            }

        return time.zip(distance)
            .toMap()
    }

    private fun solveQuadratic(a: Long, b: Long, c: Long): Pair<Double, Double>? {
        val discriminant = sqr(b) - 4 * a * c
        return if (discriminant < 0) {
            null
        } else {
            val root = sqrt(discriminant.toDouble())
            ((-b + root) / 2 * a) to ((-b - root) / 2 * a)
        }
    }

    private fun rangeSize(lower: Double, upper: Double) = (ceil(upper).roundToInt() - 1) - (floor(lower).roundToInt() + 1) + 1

    override fun executePart1(input: String) = parseInput(input).entries
        .map { (time, recordDist) ->
            val (lower, upper) = solveQuadratic(-1, time, -recordDist) ?: error("unsolvable")
            rangeSize(lower, upper)
        }
        .reduce { a, b -> a * b }

    override fun executePart2(input: String): Any {
        val (time, recordDist) = input.lines()
            .map { line ->
                line.filter { it.isDigit() }
                    .toLong()
            }

        val (lower, upper) = solveQuadratic(-1, time, -recordDist) ?: error("unsolvable")
        return rangeSize(lower, upper)
    }
}