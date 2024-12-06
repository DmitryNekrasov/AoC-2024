package day06

import assertEquals
import println
import readInput

val List<String>.start: Pair<Int, Int>
    get() = run {
        for (i in 0..lastIndex) {
            for (j in 0..this.first().lastIndex) {
                if (this[i][j] == '^') {
                    return@run i to j
                }
            }
        }
        throw RuntimeException("Should not reach here")
    }

fun part1(map: List<String>): Int {
    val (startI, startJ) = map.start
    println("start = ($startI, $startJ)")


    return map.size
}

fun part2(input: List<String>): Int {
    return input.size
}

fun main() {
    run {
        val input = readInput("Day06_test01")

        run {
            val expected = 41
            val actual = part1(input)
            assertEquals(expected, actual)
        }

        run {
            val expected = -1
            val actual = part2(input)
            assertEquals(expected, actual)
        }
    }

    run {
        val input = readInput("Day06")
        part1(input).println()
        part2(input).println()
    }
}
