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
        println("done")
        return map.tiles.count { it.appearance == 'X' } + 1
    }

    override fun executePart2(input: String): Int {
        val originalMap = GameMap.parse(input)
        val playerStart = originalMap.player.position

        // Identify candidate positions
        val candidateTiles = originalMap.tiles.filter { tile ->
            // Can't place on the player's start
            if (tile.x == playerStart.x && tile.y == playerStart.y) return@filter false
            // Can't place on walls or other invalid chars
            // Typically we'd consider placing on '.' or 'X' or other free spots.
            tile.appearance != '#' &&
                    tile.appearance != '^' && tile.appearance != 'v' &&
                    tile.appearance != '<' && tile.appearance != '>' &&
                    tile.appearance != 'W'
        }

        var loopCount = 0

        for (tile in candidateTiles) {
            // Try placing an obstruction here and simulate
            val mapCopy = originalMap.copyDeep()
            val obstructionTile = mapCopy.getTileAt(tile.x, tile.y)
            // If for some reason tile can't be found in copy, continue
            if (obstructionTile == null) continue

            obstructionTile.appearance = '#'
            if (causesLoop(mapCopy)) {
                println(loopCount)
                loopCount++
            }
        }

        return loopCount
    }

    private fun causesLoop(map: GameMap): Boolean {
        // We run a simulation and track visited states
        val visitedStates = mutableSetOf<Triple<Int, Int, Char>>()

        // We'll run until we either hit 'W' or detect a loop
        while (map.tiles.none { it.appearance == 'W' }) {
            val state = Triple(map.player.position.x, map.player.position.y, map.player.playerChar)
            if (!visitedStates.add(state)) {
                // We revisited a state => loop detected
                return true
            }
            map.movePlayer()
        }

        // If we got 'W', no loop
        return false
    }

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
                        // Out of known map, add 'W' and return
                        tiles.add(Tile(Int.MAX_VALUE, Int.MAX_VALUE, 'W'))
                        return
                    }
                    nextTile.appearance == '#' -> {
                        player.playerChar = player.rotate90()
                    }
                    else -> {
                        // Mark old position as visited
                        getTileAt(px, py)?.appearance = 'X'
                        player.position = nextTile
                        return
                    }
                }
            }
        }

        fun getTileAt(x: Int, y: Int): Tile? = tiles.find { it.x == x && it.y == y }

        fun copyDeep(): GameMap {
            val newTiles = tiles.map { Tile(it.x, it.y, it.appearance) }.toMutableList()
            val playerTile = newTiles.first { it.x == player.position.x && it.y == player.position.y && it.appearance == player.playerChar }
            val newPlayer = Player(playerChar = player.playerChar, position = playerTile)
            return GameMap(newTiles, newPlayer)
        }

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
