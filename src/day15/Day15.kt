package day15

import assertEquals
import println
import readInput

fun List<String>.parse() =
    partition { it.startsWith("#") }.let { (first, second) -> first.map(String::toCharArray) to second.joinToString("") }

fun List<CharArray>.shiftLeft(startI: Int, startJ: Int): Pair<Int, Int> {
    var j = startJ - 1
    while (this[startI][j] == 'O') j--
    if (this[startI][j] == '.') {
        this[startI][j] = 'O'
        this[startI][startJ - 1] = '@'
        this[startI][startJ] = '.'
        return startI to j - 1
    }
    return startI to startJ
}

fun List<CharArray>.shiftRight(startI: Int, startJ: Int): Pair<Int, Int> {
    // TODO
    return startI to startI
}

fun List<CharArray>.shiftTop(startI: Int, startJ: Int): Pair<Int, Int> {
    // TODO
    return startI to startI
}

fun List<CharArray>.shiftBottom(startI: Int, startJ: Int): Pair<Int, Int> {
    // TODO
    return startI to startI
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

val List<CharArray>.hash: Int
    get() {
        var result = 0
        for (i in indices) {
            for (j in first().indices) {
                if (this[i][j] == 'O') {
                    result += i * 100 + j
                }
            }
        }
        return result
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

    return grid.hash
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
