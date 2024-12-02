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
            if (this[i - 1] compareTo this[i] != compare || abs(this[i - 1] - this[i]) > 3) {
                return false
            }
        }
        return true
    }

    return input.count { it.isSafe() }
}

fun part2(input: List<List<Int>>): Int {
    fun List<Int>.isSafe(ignoreIndex: Int): Boolean {
        val compare = when (ignoreIndex) {
            0 -> this[1] compareTo this[2]
            1 -> this[0] compareTo this[2]
            else -> this[0] compareTo this[1]
        }
        var prev = if (ignoreIndex == 0) this[1] else this[0]
        for (i in (if (ignoreIndex > 2) 1 else 2)..lastIndex) {
            if (i == ignoreIndex) continue
            if (prev compareTo this[i] != compare || abs(prev - this[i]) > 3) {
                return false
            }
            prev = this[i]
        }
        return true
    }

    return input.count { list -> (0..list.lastIndex).any { list.isSafe(it) } }
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
            val expected = 4
            val actual = part2(input)
            assertEquals(expected, actual)
        }
    }

    run {
        val input = readInput("Day02").toMatrix()
        part1(input).println()
        part2(input).println()
    }
}
