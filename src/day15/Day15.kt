package day15

import assertEquals
import println
import readInput

fun List<String>.parse() =
    partition { it.startsWith("#") }.let { (first, second) -> first.map(String::toCharArray) to second.joinToString("") }

fun List<CharArray>.shiftLeft(startI: Int, startJ: Int): Pair<Int, Int> {
    // TODO
    return -1 to -1
}

fun List<CharArray>.shiftRight(startI: Int, startJ: Int): Pair<Int, Int> {
    // TODO
    return -1 to -1
}

fun List<CharArray>.shiftTop(startI: Int, startJ: Int): Pair<Int, Int> {
    // TODO
    return -1 to -1
}

fun List<CharArray>.shiftBottom(startI: Int, startJ: Int): Pair<Int, Int> {
    // TODO
    return -1 to -1
}

val List<CharArray>.start: Pair<Int, Int>
    get() {
        for (i in indices) {
            for (j in first().indices) {
                if (this[i][j] == '@') {
                    return i to j
                }
            }
        }
        throw RuntimeException("Should not reach here")
    }

fun part1(grid: List<CharArray>, commands: String): Int {
    var (i, j) = grid.start
    for (command in commands) {
        val (nextI, nextJ) = when (command) {
            '<' -> grid.shiftLeft(i, j)
            '>' -> grid.shiftRight(i, j)
            '^' -> grid.shiftTop(i, j)
            'v' -> grid.shiftBottom(i, j)
            else -> throw RuntimeException("Should not reach here")
        }
        i = nextI; j = nextJ
    }

    return commands.length
}

fun part2(grid: List<CharArray>, commands: String): Int {
    return commands.length
}

fun main() {
    run {
        val (grid, commands) = readInput("Day15_test01").parse()

        run {
            val expected = 10092
            val actual = part1(grid, commands)
            assertEquals(expected, actual)
        }

        run {
            val expected = -1
            val actual = part2(grid, commands)
            assertEquals(expected, actual)
        }
    }

    run {
        val (grid, commands) = readInput("Day15").parse()
        part1(grid, commands).println()
        part2(grid, commands).println()
    }
}
