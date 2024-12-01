package day02

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
    /**
     * Input: Day02_test01
     */
    run {
        val input = readInput("Day02_test01")

        /**
         * Part 1
         * Output: -1
         */
        run {
            val expected = -1
            val actual = part1(input)
            assertEquals(expected, actual)
        }

        /**
         * Part 2
         * Output: -1
         */
        run {
            val expected = -1
            val actual = part2(input)
            assertEquals(expected, actual)
        }
    }

    /**
     * Input: Day02
     */
    run {
        val input = readInput("Day02")
        part1(input).println()
        part2(input).println()
    }
}
