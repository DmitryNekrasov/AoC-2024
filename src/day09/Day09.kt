package day09

import assertEquals
import println
import readInput

fun part1(input: String): Int {
    return input.length
}

fun part2(input: String): Int {
    return input.length
}

fun main() {
    run {
        val input = readInput("Day09_test01").first()

        run {
            val expected = 1928
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
        val input = readInput("Day09").first()
        part1(input).println()
        part2(input).println()
    }
}
