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

fun part1(input: List<List<String>>): Int {
    input.joinToString("\n").println()

    return input.size
}

fun part2(input: List<List<String>>): Int {
    return input.size
}

fun main() {
    run {
        val input = readInput("Day25_test01").parse()

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
        val input = readInput("Day25").parse()
        part1(input).println()
        part2(input).println()
    }
}
