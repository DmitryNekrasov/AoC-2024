package day20

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
        val input = readInput("Day20_test01")

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
        val input = readInput("Day20")
        part1(input).println()
        part2(input).println()
    }
}
