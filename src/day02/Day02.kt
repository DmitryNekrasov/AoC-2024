package day02

import assertEquals
import println
import readInput
import kotlin.math.abs

fun List<String>.toMatrix() = this.map { it.split(" ").map(String::toInt) }

fun part1(input: List<List<Int>>): Int {
    fun List<Int>.isSafe(): Boolean {
        val compare = this[0] compareTo this[1]
        for (i in 1..lastIndex) {
            if ((this[i - 1] compareTo this[i]) != compare || abs(this[i - 1] - this[i]) > 3) {
                return false
            }
        }
        return true
    }
    return input.count { it.isSafe() }
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
