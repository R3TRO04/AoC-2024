package com.r3tro04.days

import com.r3tro04.structure.AoCDay
import com.r3tro04.util.day
import kotlinx.datetime.LocalDate

object Day6 : AoCDay {
    override val day: LocalDate = day(6)

    override fun executePart1(input: String): Any {
        val map = Map.parse(input)
        while (map.tiles.none { it.appearance == 'W' }) {
            println("Player at position: ${map.player.position} facing: ${map.player.playerChar}")
            map.movePlayer()
        }
        println("Final Map:")
        println(map.prettyPrint())
        return map.tiles.count { it.appearance == 'X' } + 1
    }

    override fun executePart2(input: String): Any = "(not implemented)"

    data class Player(
        var playerChar: Char = '^',
        var position: Tile
    ) {
        fun moveDirection(): Direction = when (playerChar) {
            '^' -> Direction.UP
            'v' -> Direction.DOWN
            '<' -> Direction.LEFT
            '>' -> Direction.RIGHT
            else -> error("Invalid player character")
        }

        fun rotate90(): Char = when (playerChar) {
            '^' -> '>'
            '>' -> 'v'
            'v' -> '<'
            '<' -> '^'
            else -> error("Invalid player character")
        }
    }

    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }

    data class Map(
        val tiles: MutableList<Tile>,
        val player: Player
    ) {
        fun movePlayer() {
            repeat(4) { rotations ->
                val nextTile = tiles.find {
                    when (player.moveDirection()) {
                        Direction.UP -> it.x == player.position.x && it.y == player.position.y - 1
                        Direction.DOWN -> it.x == player.position.x && it.y == player.position.y + 1
                        Direction.RIGHT -> it.x == player.position.x + 1 && it.y == player.position.y
                        Direction.LEFT -> it.x == player.position.x - 1 && it.y == player.position.y
                    }
                }

                when {
                    nextTile == null -> {
                        tiles.add(Tile(Int.MAX_VALUE, Int.MAX_VALUE, 'W'))
                        println("Player moved into uncharted territory")
                        return
                    }
                    nextTile.appearance == '#' -> {
                        player.playerChar = player.rotate90()
                        println("Player hit a wall and rotated to: ${player.playerChar}")
                    }
                    else -> {
                        tiles.find { it.x == player.position.x && it.y == player.position.y }?.appearance = 'X'
                        player.position = nextTile
                        println("Player moved to position: ${player.position}")
                        return
                    }
                }
            }
            println("Player stuck and cannot move")
        }

        fun prettyPrint(): String {
            tiles.removeIf { it.appearance == 'W' }
            val maxX = tiles.maxOfOrNull { it.x } ?: 0
            val maxY = tiles.maxOfOrNull { it.y } ?: 0
            val mapGrid = Array(maxY + 1) { CharArray(maxX + 1) { ' ' } }
            tiles.forEach { mapGrid[it.y][it.x] = it.appearance }
            mapGrid[player.position.y][player.position.x] = player.playerChar
            return mapGrid.joinToString("\n") { it.concatToString() }
        }

        companion object {
            fun parse(input: String): Map {
                val tiles = mutableListOf<Tile>()
                var player = Player(position = Tile(1, 1, '^'))

                input.lineSequence().forEachIndexed { y, line ->
                    line.forEachIndexed { x, char ->
                        tiles.add(Tile(x, y, char))
                        if (char == '^') player = Player(position = Tile(x, y, char))
                    }
                }

                return Map(tiles, player)
            }
        }
    }

    data class Tile(val x: Int, val y: Int, var appearance: Char)
}