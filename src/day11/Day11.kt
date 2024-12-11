package day11

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
        val input = readInput("Day11_test01").first().split(" ").map(String::toInt)

        run {
            val expected = 55312
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
        val input = readInput("Day11").first().split(" ").map(String::toInt)
        part1(input).println()
        part2(input).println()
    }
}
