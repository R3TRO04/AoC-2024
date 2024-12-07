package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day6 : AoCDay {
    override val day: LocalDate = day(6)

    override fun executePart1(input: String): Any {
        val map = Map.parse(input)
        while(map.tiles.none { it.appearance == 'W' }) {
            map.movePlayer()
        }
        return map.tiles.count {
            it.appearance == 'X'
        }
    }

    override fun executePart2(input: String): Any {
        return "(not implemented)"
    }

    data class Player(
        var playerChar: Char = '^',
        var position: Tile
    ) {
        fun moveDirection() : Direction {
            return when (playerChar) {
                '^' -> Direction.UP
                'v' -> Direction.DOWN
                '<' -> Direction.LEFT
                '>' -> Direction.RIGHT
                else -> error("wtf")
            }
        }
    }

    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }

    data class Map(
        val tiles: MutableList<Tile>,
        val player: Player
    ) {
        fun movePlayer(): Unit {
            var nextIndex = tiles.indexOfFirst {
                when(player.moveDirection()) {
                    Direction.UP -> it.x == player.position.x && it.y == player.position.y - 1
                    Direction.DOWN -> it.x == player.position.x && it.y == player.position.y + 1
                    Direction.RIGHT -> it.x + 1 == player.position.x && it.y == player.position.y
                    Direction.LEFT -> it.x - 1 == player.position.x && it.y == player.position.y
                }
            }

            if (nextIndex == -1) {
                tiles.add(Tile(Int.MAX_VALUE, Int.MAX_VALUE, 'W'))
            } else if (tiles[nextIndex].appearance == '#') {
                while(tiles[nextIndex].appearance == '#') {
                    player.playerChar = when (player.moveDirection()) {
                        Direction.UP -> '>'
                        Direction.RIGHT -> 'v'
                        Direction.DOWN -> '<'
                        Direction.LEFT -> '^'
                    }
                    nextIndex = tiles.indexOfFirst {
                        when (player.moveDirection()) {
                            Direction.UP -> it.x + 1 == player.position.x && it.y == player.position.y
                            Direction.DOWN -> it.x - 1 == player.position.x && it.y == player.position.y
                            Direction.RIGHT -> it.x == player.position.x && it.y == player.position.y + 1
                            Direction.LEFT -> it.x == player.position.x && it.y == player.position.y - 1
                        }
                    }
                }
                tiles.first { player.position == it }.appearance = 'X'
                player.position = tiles[nextIndex]
            } else {
                tiles.first {
                    player.position.x == it.x && player.position.y == it.y
                }.appearance = 'X'
                player.position = tiles[nextIndex]
            }
        }

        companion object {
            fun parse(input: String): Map {
                var player = Player(position = Tile(1,1, '^'))
                input.lines().indices.forEach { y ->
                    input.lines()[y].indices.forEach { x ->
                        if (input.lines()[x][y] == '^') {
                            player = Player(position = Tile(x, y, '^'))
                        }
                    }
                }
                return Map(
                    buildList {
                        input.lineSequence().forEachIndexed { indexLine, s ->
                            s.forEachIndexed { indexChar, c ->
                                add(Tile(indexChar, indexLine, c))
                            }
                        }
                    }.toMutableList(),
                    player
                )
            }
        }
    }

    data class Tile(val x: Int, val y: Int, var appearance: Char)
}