package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day4 : AoCDay {
    override val day: LocalDate = day(4)

    override fun executePart1(input: String): Any {
        val lines = input.lines()
        val xmasPattern = "XMAS"

        val directions: List<(Int, Int, Int) -> Char?> = listOf(
            { i, j, k -> lines.getOrNull(i)?.getOrNull(j + k) }, // Right
            { i, j, k -> lines.getOrNull(i)?.getOrNull(j - k) }, // Left
            { i, j, k -> lines.getOrNull(i + k)?.getOrNull(j) }, // Down
            { i, j, k -> lines.getOrNull(i - k)?.getOrNull(j) }, // Up
            { i, j, k -> lines.getOrNull(i + k)?.getOrNull(j + k) }, // Diagonal Right Down
            { i, j, k -> lines.getOrNull(i + k)?.getOrNull(j - k) }, // Diagonal Right Up
            { i, j, k -> lines.getOrNull(i - k)?.getOrNull(j + k) }, // Diagonal Left Down
            { i, j, k -> lines.getOrNull(i - k)?.getOrNull(j - k) }  // Diagonal Left Up
        )

        return lines.indices.sumOf { i ->
            lines[i].indices.sumOf { j ->
                directions.count { direction ->
                    val chars = xmasPattern.indices.mapNotNull { k -> direction(i, j, k) }
                    chars.size == xmasPattern.length && chars.joinToString("") == xmasPattern
                }
            }
        }
    }

    override fun executePart2(input: String): Any {
        val lines = input.lines()
        val masPatterns = setOf("MAS", "SAM")

        return (1 until lines.size - 1).sumOf { i ->
            (1 until lines[0].length - 1).count { j ->
                val topLeftToBottomRight = (0..2).mapNotNull { k ->
                    lines.getOrNull(i - 1 + k)?.getOrNull(j - 1 + k)
                }.joinToString("")
                val topRightToBottomLeft = (0..2).mapNotNull { k ->
                    lines.getOrNull(i - 1 + k)?.getOrNull(j + 1 - k)
                }.joinToString("")

                topLeftToBottomRight in masPatterns && topRightToBottomLeft in masPatterns
            }
        }
    }
}
