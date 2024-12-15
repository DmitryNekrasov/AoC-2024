package day15

import assertEquals
import println
import readInput

fun List<String>.parse() =
    partition { it.startsWith("#") }.let { (first, second) -> first.map(String::toCharArray) to second.joinToString("") }

fun part1(grid: List<CharArray>, commands: String): Int {
    return commands.length
}

fun part2(grid: List<CharArray>, commands: String): Int {
    return commands.length
}

fun main() {
    run {
        val (grid, commands) = readInput("Day15_test01").parse()

        run {
            val expected = 10092
            val actual = part1(grid, commands)
            assertEquals(expected, actual)
        }

        run {
            val expected = -1
            val actual = part2(grid, commands)
            assertEquals(expected, actual)
        }
    }

    run {
        val (grid, commands) = readInput("Day15").parse()
        part1(grid, commands).println()
        part2(grid, commands).println()
    }
}
