package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day4 : AoCDay {
    override val day: LocalDate = day(4)
    private val searchChars = "XMAS"

    override fun executePart1(input: String): Any {
        val length = input.lines().first().length
        val depth = input.lines().size
        val charArray = Array(length) {
            CharArray(depth)
        }
        val indices = buildMap {
            put(DirectionStart.LEFT, buildList {
                for (i in 0 until length - 1) {
                    if (i + 3 <= length) {
                        add(
                            listOf(
                                i,
                                i + 1,
                                i + 2,
                                i + 3,
                            )
                        )
                    }
                }
            })
            put( DirectionStart.RIGHT, buildList {
                for (i in 0 until length - 1) {
                    if (i + 3 <= length) {
                        add(
                            listOf(
                                i + 3,
                                i + 2,
                                i + 1,
                                i,
                            )
                        )
                    }
                }
            })
            put(DirectionStart.UP, buildList {
                for (i in 0 until depth - 1) {
                    if (i + 3 <= depth) {
                        add(
                            listOf(
                                i,
                                i + 1,
                                i + 2,
                                i + 3,
                            )
                        )
                    }
                }
            })
            put(DirectionStart.DOWN, buildList {
                for (i in 0 until depth - 1) {
                    if (i + 3 <= depth) {
                        add(
                            listOf(
                                i + 3,
                                i + 2,
                                i + 1,
                                i,
                            )
                        )
                    }
                }
            })
        }

        return buildList {
            for (i in 0 until length - 1) {
               add(
                   indices.filter {
                        it.key == DirectionStart.LEFT || it.key == DirectionStart.RIGHT
                    }.map {
                        it.value
                    }.map {
                        it.flatten().map { index ->
                            if (index == 140) charArray[index - 1][i]
                            else charArray[index][i]
                        }
                    }.count {
                        it.toString() == searchChars
                    }
               )
            }
            for (i in 0 until depth - 1) {
                add(
                    indices.filter {
                        it.key == DirectionStart.UP || it.key == DirectionStart.DOWN
                    }.map {
                        it.value
                    }.map {
                        it.flatten().map { index ->
                            if (index == 140) charArray[i][index - 1]
                            else charArray[i][index]

                        }
                    }.count {
                        it.toString() == searchChars
                    }
                )
            }
        }.sum()
    }

    override fun executePart2(input: String): Any {
        return "(not implemented)"
    }

    private enum class DirectionStart {
        LEFT,
        RIGHT,
        UP,
        DOWN,
    }
}