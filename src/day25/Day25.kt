package day25

import assertEquals
import println
import readInput

fun List<String>.parse(): List<List<String>> {
    val result = mutableListOf<MutableList<String>>()
    result += mutableListOf<String>()
    for (line in this) {
        if (line.isBlank()) {
            result += mutableListOf<String>()
        } else {
            result.last() += line
        }
    }
    return result
}

fun List<String>.toHeights(): IntArray {
    val n = size
    val m = first().length
    val result = IntArray(m) { 0 }
    for (i in 0..<n) {
        for (j in 0..<m) {
            if (this[i][j] == '#') {
                result[j] += 1
            }
        }
    }
    return result
}

infix fun IntArray.isCompatibleWith(o: IntArray) = zip(o).all { (h1, h2) -> h1 + h2 <= 7 }

fun part1(input: List<List<String>>): Int {
    val (locks, keys) = input.partition { it.first().startsWith('#') }.let { (locks, keys) ->
        locks.map { it.toHeights() } to keys.map { it.toHeights() }
    }

    var result = 0
    for (lock in locks) {
        for (key in keys) {
            if (lock isCompatibleWith key) {
                result++
            }
        }
    }

    return result
}

fun main() {
    run {
        val input = readInput("Day25_test01").parse()

        run {
            val expected = 3
            val actual = part1(input)
            assertEquals(expected, actual)
        }
    }

    run {
        val input = readInput("Day25").parse()
        part1(input).println()
    }
}
