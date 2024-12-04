package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day4 : AoCDay {
    override val day: LocalDate = day(4)
    private val searchChars = "XMAS"

    // should be 2554
    override fun executePart1(input: String): Any {
        val length = input.lines().first().lastIndex
        val depth = input.lines().lastIndex
        val charArray = Array(length + 1) {
            CharArray(depth + 1)
        }
        input.lineSequence().forEachIndexed { indexLine, s ->
            s.forEachIndexed { indexChar, c ->
                charArray[indexChar][indexLine] = c
            }
        }
        val indices = buildMap {
            put(DirectionStart.LEFT, buildList {
                for (i in 0..length) {
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
            put(DirectionStart.RIGHT, buildList {
                for (i in 0..length) {
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
                for (i in 0..depth) {
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
                for (i in 0..depth) {
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
            for (i in 0..depth) {
               add(
                   indices.filter {
                        it.key == DirectionStart.LEFT || it.key == DirectionStart.RIGHT
                    }.map {
                        it.value
                    }.map {
                        it.map { innerList ->
                            innerList.map { index ->
                                charArray[index][i]
                            }
                        }
                    }.sumOf {
                        it.count { chars ->
                            val s = chars[0].toString() + chars[1] + chars[2] + chars[3]
                            s == searchChars
                        }
                    }
               )
            }
            for (i in 0..length) {
                add(
                    indices.filter {
                        it.key == DirectionStart.UP || it.key == DirectionStart.DOWN
                    }.map {
                        it.value
                    }.map {
                        it.map { innerList ->
                            innerList.map { index ->
                                charArray[i][index]
                            }
                        }
                    }.sumOf {
                        it.count { chars ->
                            val s = chars[0].toString() + chars[1] + chars[2] + chars[3]
                            s == searchChars
                        }
                    }
                )
            }
            for (i in 0..length) {
                for (j in 0..depth) {
                    add(
                        buildList {
                            add(
                                buildList {
                                    for (d in 0..3) {
                                        add(charArray.diagonalRightUp(i, j, d))
                                    }
                                }
                            )
                            add(
                                buildList {
                                    for (d in 0..3) {
                                        add(charArray.diagonalRightDown(i, j, d))
                                    }
                                }
                            )
                            add(
                                buildList {
                                    for (d in 0..3) {
                                        add(charArray.diagonalLeftUp(i, j, d))
                                    }
                                }
                            )
                            add(
                                buildList {
                                    for (d in 0..3) {
                                        add(charArray.diagonalLeftDown(i, j, d))
                                    }
                                }
                            )
                        }.map {
                            it.filterNotNull()
                        }.filter {
                            it.size == 4
                        }.count { chars ->
                            val s = chars[0].toString() + chars[1] + chars[2] + chars[3]
                            s == searchChars
                        }
                    )
                }
            }
        }.sum()
    }

    // should be 1916
    override fun executePart2(input: String): Any {
        return "(not implemented)"
    }

    private enum class DirectionStart {
        LEFT,
        RIGHT,
        UP,
        DOWN,
    }

    private fun Array<CharArray>.diagonalRightDown(i: Int, j: Int, index: Int) : Char? {
        var res : Char? = null
        runCatching {
            this[i+index][j+index]
        }.onSuccess {
            res = it
        }
        return res
    }
    private fun Array<CharArray>.diagonalRightUp(i: Int, j: Int, index: Int) : Char? {
        var res : Char? = null
        runCatching {
            this[i+index][j-index]
        }.onSuccess {
            res = it
        }
        return res
    }
    private fun Array<CharArray>.diagonalLeftDown(i: Int, j: Int, index: Int) : Char? {
        var res : Char? = null
        runCatching {
            this[i-index][j+index]
        }.onSuccess {
            res = it
        }
        return res
    }
    private fun Array<CharArray>.diagonalLeftUp(i: Int, j: Int, index: Int) : Char? {
        var res : Char? = null
        runCatching {
            this[i-index][j-index]
        }.onSuccess {
            res = it
        }
        return res
    }
}