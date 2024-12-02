package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day2 : AoCDay {
    override val day: LocalDate = day(2)

    override fun executePart1(input: String): Any {
        return input.lineSequence().map { line ->
            line.split(" ").map(String::toInt)
        }.count {
            var isValid = true
            var asc = false
            var desc = false

            it.zipWithNext { l, r ->
                if (!asc && !desc) {
                    when {
                        l < r -> asc = true
                        l > r -> desc = true
                        else -> isValid = false
                    }
                }
                if (asc) {
                    if (l >= r || r - l !in 1..3) {
                        isValid = false
                    }
                }
                if (desc) {
                    if (l <= r ||l - r !in 1..3) {
                        isValid = false
                    }
                }
            }
            isValid
        }
    }

    override fun executePart2(input: String): Any {
        return input.lineSequence().map { line ->
            line.split(" ").map(String::toInt)
        }.count {
            val listOLists = mutableListOf<List<Int>>()
            it.forEachIndexed { index, _ ->
                val lizt = mutableListOf(it).flatten().toMutableList()
                lizt.removeAt(index)
                listOLists.add(lizt)
            }
            listOLists.any { lisst ->
                var isValid = true
                var asc = false
                var desc = false

                lisst.zipWithNext { l, r ->
                    if (!asc && !desc) {
                        when {
                            l < r -> asc = true
                            l > r -> desc = true
                            else -> isValid = false
                        }
                    }
                    if (asc) {
                        if (l >= r || r - l !in 1..3) {
                            isValid = false
                        }
                    }
                    if (desc) {
                        if (l <= r ||l - r !in 1..3) {
                            isValid = false
                        }
                    }
                }
                isValid
            }
        }
    }
}