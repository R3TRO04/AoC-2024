package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day6 : AoCDay {
    override val day: LocalDate = day(6)

    override fun executePart1(input: String): Int {
        val map = GameMap.parse(input)
        while (map.tiles.none { it.appearance == 'W' }) {
            map.movePlayer()
        }
        return map.tiles.count { it.appearance == 'X' } + 1
    }

    override fun executePart2(input: String): Any = "(not implemented)"

    data class Player(
        var playerChar: Char,
        var position: Tile
    ) {
        fun moveDirection(): Direction = when (playerChar) {
            '^' -> Direction.UP
            'v' -> Direction.DOWN
            '<' -> Direction.LEFT
            '>' -> Direction.RIGHT
            else -> error("Invalid player character: $playerChar")
        }

        fun rotate90(): Char = when (playerChar) {
            '^' -> '>'
            '>' -> 'v'
            'v' -> '<'
            '<' -> '^'
            else -> error("Invalid player character: $playerChar")
        }
    }

    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }

    data class Tile(val x: Int, val y: Int, var appearance: Char)

    data class GameMap(
        val tiles: MutableList<Tile>,
        val player: Player
    ) {
        fun movePlayer() {
            repeat(4) {
                val (px, py) = player.position
                val (dx, dy) = when (player.moveDirection()) {
                    Direction.UP -> 0 to -1
                    Direction.DOWN -> 0 to 1
                    Direction.LEFT -> -1 to 0
                    Direction.RIGHT -> 1 to 0
                }

                val nextTile = getTileAt(px + dx, py + dy)

                when {
                    nextTile == null -> {
                        tiles.add(Tile(Int.MAX_VALUE, Int.MAX_VALUE, 'W'))
                        return
                    }
                    nextTile.appearance == '#' -> {
                        player.playerChar = player.rotate90()
                    }
                    else -> {
                        getTileAt(px, py)?.appearance = 'X'
                        player.position = nextTile
                        return
                    }
                }
            }
        }

        private fun getTileAt(x: Int, y: Int): Tile? = tiles.find { it.x == x && it.y == y }

        companion object {
            fun parse(input: String): GameMap {
                val tiles = input.lineSequence()
                    .flatMapIndexed { y, line ->
                        line.mapIndexed { x, char -> Tile(x, y, char) }
                    }.toMutableList()

                val playerTile = tiles.firstOrNull { it.appearance == '^' }
                    ?: Tile(1, 1, '^').also { tiles.add(it) }

                val player = Player(playerChar = '^', position = playerTile)
                return GameMap(tiles, player)
            }
        }
    }
}
