package day19

import assertEquals
import println
import readInput

fun List<String>.parse() = first().split(",") to slice(2..lastIndex)

fun part1(patterns: List<String>, designs: List<String>): Int {
    println(patterns)
    println(designs.joinToString("\n"))

    return patterns.size
}

fun part2(patterns: List<String>, designs: List<String>): Int {
    return patterns.size
}

fun main() {
    run {
        val (patterns, designs) = readInput("Day19_test01").parse()

        run {
            val expected = 6
            val actual = part1(patterns, designs)
            assertEquals(expected, actual)
        }

        run {
            val expected = -1
            val actual = part2(patterns, designs)
            assertEquals(expected, actual)
        }
    }

    run {
        val (patterns, designs) = readInput("Day19").parse()
        part1(patterns, designs).println()
        part2(patterns, designs).println()
    }
}
