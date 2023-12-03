package com.rtarita.days

import com.rtarita.structure.AoCDay
import com.rtarita.util.day
import com.rtarita.util.exactlyOrNull
import com.rtarita.util.indexOfFirst
import kotlinx.datetime.LocalDate
import kotlin.math.max
import kotlin.math.min

object Day3 : AoCDay {
    override val day: LocalDate = day(3)

    private fun getAdjacent(x: Int, y: Int, xlast: Int, ylast: Int): List<Pair<Int, Int>> {
        val xrange = max(x - 1, 0)..min(x + 1, xlast)
        val yrange = max(y - 1, 0)..min(y + 1, ylast)
        return xrange.flatMap { xAdj ->
            yrange.map { yAdj ->
                xAdj to yAdj
            }
        }
    }

    private fun adjacentNumbers(posX: Int, posY: Int, table: List<String>): List<Int> = buildList {
        for (y in max(0, posY - 1)..min(posY + 1, table.size)) {
            val leftNum = table[y].substring(0, posX)
                .takeLastWhile { it.isDigit() }
            val rightNum = table[y]
                .substring(posX + 1)
                .takeWhile { it.isDigit() }

            if (table[y][posX].isDigit()) {
                add((leftNum + table[y][posX] + rightNum).toInt())
            } else {
                leftNum.takeIf { it.isNotEmpty() }?.let { add(it.toInt()) }
                rightNum.takeIf { it.isNotEmpty() }?.let { add(it.toInt()) }
            }
        }
    }

    override fun executePart1(input: String): Any {
        val table = input.lines()
        var sum = 0
        for (y in table.indices) {
            val row = table[y]
            var x = 0
            while (x < row.length) {
                if (!row[x].isDigit()) {
                    ++x
                } else {
                    val end = row.indexOfFirst(x) { !it.isDigit() }.takeIf { it != -1 } ?: row.length
                    val include = (x..<end).flatMap { getAdjacent(it, y, row.lastIndex, table.lastIndex) }
                        .toSet()
                        .any { (xAdj, yAdj) ->
                            val char = table[yAdj][xAdj]
                            char != '.' && !char.isDigit()
                        }
                    if (include) {
                        sum += row.substring(x, end).toInt()
                    }
                    x = end
                }
            }
        }
        return sum
    }

    override fun executePart2(input: String): Any {
        val table = input.lines()
        var sum = 0
        for (y in table.indices) {
            for (x in table[y].indices) {
                if (table[y][x] == '*') {
                    adjacentNumbers(x, y, table).exactlyOrNull(2)
                        ?.let { sum += it.reduce { acc, elem -> acc * elem } }
                }
            }
        }
        return sum
    }
}