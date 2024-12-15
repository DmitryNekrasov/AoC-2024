package day15

import assertEquals
import println
import readInput

fun List<String>.parse() =
    partition { it.startsWith("#") }.let { (first, second) -> first.map(String::toCharArray) to second.joinToString("") }

fun List<CharArray>.shift(startI: Int, startJ: Int, di: Int, dj: Int): Pair<Int, Int> {
    var i = startI + di
    var j = startJ + dj
    while (this[i][j] == 'O') {
        i += di
        j += dj
    }
    if (this[i][j] == '.') {
        this[i][j] = 'O'
        this[startI + di][startJ + dj] = '@'
        this[startI][startJ] = '.'
        return startI + di to startJ + dj
    }
    return startI to startJ
}

fun List<CharArray>.shiftLeft(i: Int, j: Int) = shift(i, j, 0, -1)

fun List<CharArray>.shiftRight(i: Int, j: Int) = shift(i, j, 0, 1)

fun List<CharArray>.shiftTop(i: Int, j: Int) = shift(i, j, -1, 0)

fun List<CharArray>.shiftBottom(i: Int, j: Int) = shift(i, j, 1, 0)

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
