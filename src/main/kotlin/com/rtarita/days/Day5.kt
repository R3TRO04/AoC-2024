package com.rtarita.days

import com.rtarita.structure.AoCDay
import com.rtarita.util.day
import kotlinx.datetime.LocalDate

object Day5 : AoCDay {
    private data class MapTriple(
        val destStart: Long,
        val origStart: Long,
        val rangeSize: Long
    ) {
        val origRange: LongRange
            get() = origStart..<(origStart + rangeSize)

        val destRange: LongRange
            get() = destStart..<(destStart + rangeSize)
    }

    private class ConversionMap(
        private val instructions: Set<MapTriple>
    ) {

        fun origToDest(orig: Long): Long {
            for (instruction in instructions) {
                if (orig in instruction.origRange) {
                    return orig + (instruction.destStart - instruction.origStart)
                }
            }
            return orig
        }

        fun destToOrig(dest: Long): Long {
            for (instruction in instructions) {
                if (dest in instruction.destRange) {
                    return dest + (instruction.origStart - instruction.destStart)
                }
            }
            return dest
        }
    }


    override val day: LocalDate = day(5)

    private fun parseAlmanac(input: String): Pair<Set<Long>, List<ConversionMap>> {
        val entries = input.split("\n\n")
        val seeds = entries[0].removePrefix("seeds: ")
            .trim()
            .split(' ')
            .map { it.toLong() }
            .toSet()

        val maps = entries.drop(1)
            .map { entry ->
                ConversionMap(
                    entry.lines()
                        .drop(1)
                        .map {
                            val (destStart, origStart, rangeSize) = it.split(" ")
                            MapTriple(destStart.toLong(), origStart.toLong(), rangeSize.toLong())
                        }.toSet()
                )
            }

        return seeds to maps
    }

    override fun executePart1(input: String): Any {
        val (seeds, maps) = parseAlmanac(input)
        return seeds.minOf { seed ->
            maps.fold(seed) { acc, map ->
                map.origToDest(acc)
            }
        }
    }

    override fun executePart2(input: String): Any {
        val (seeds, maps) = parseAlmanac(input)
        val seedRanges = seeds.chunked(2) { (start, size) ->
            start..<(start + size)
        }

        return generateSequence(0L) { it + 1 }
            .first { location ->
                val seed = maps.foldRight(location) { map, acc ->
                    map.destToOrig(acc)
                }
                seedRanges.any { seed in it }
            }
    }
}