package day22

import assertEquals
import println
import readInput

fun part1(input: List<Int>): Int {
    return input.size
}

fun part2(input: List<Int>): Int {
    return input.size
}

fun main() {
    run {
        val input = readInput("Day22_test01").map(String::toInt)

        run {
            val expected = -1
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
        val input = readInput("Day22").map(String::toInt)
        part1(input).println()
        part2(input).println()
    }
}
