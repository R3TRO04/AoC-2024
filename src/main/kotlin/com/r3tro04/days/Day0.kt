package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day0 : AoCDay {
    override val day: LocalDate = day(0)

    override fun executePart1(input: String): Any {
        return "Test day part 1 executed successfully (this is Day \"0\", which is intended either for testing the framework or as a fallback)"
    }

    override fun executePart2(input: String): Any {
        return "Test day part 2 executed successfully (this is Day \"0\", which is intended either for testing the framework or as a fallback)"
    }
}