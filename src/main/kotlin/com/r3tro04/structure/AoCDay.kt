package com.r3tro04.structure

import com.r3tro04.structure.fmt.Formatter
import com.r3tro04.structure.fmt.GenericFormatter
import kotlinx.datetime.LocalDate

interface AoCDay {
    val day: LocalDate
    fun executePart1(input: String): Any?
    fun executePart2(input: String): Any?

    val outputFormatter1: Formatter<Any?>
        get() = GenericFormatter
    val outputFormatter2: Formatter<Any?>
        get() = GenericFormatter
}