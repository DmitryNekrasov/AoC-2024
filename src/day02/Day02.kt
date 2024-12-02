package day02

import assertEquals
import println
import readInput

fun List<String>.toMatrix() = this.map { it.split(" ").map(String::toInt) }

fun part1(input: List<List<Int>>): Int {
    println(input)

    return input.size
}

fun part2(input: List<List<Int>>): Int {
    return input.size
}

fun main() {
    run {
        val input = readInput("Day02_test01").toMatrix()

        run {
            val expected = 2
            val actual = part1(input)
            assertEquals(expected, actual)
        }

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
        val input = readInput("Day02").toMatrix()
        part1(input).println()
        part2(input).println()
    }
}
