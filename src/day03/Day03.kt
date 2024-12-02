package day03

import assertEquals
import println
import readInput

fun part1(input: List<String>): Int {
    return input.size
}

fun part2(input: List<String>): Int {
    return input.size
}

fun main() {
    run {
        val input = readInput("Day03_test01")

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
        val input = readInput("Day03")
        part1(input).println()
        part2(input).println()
    }
}
