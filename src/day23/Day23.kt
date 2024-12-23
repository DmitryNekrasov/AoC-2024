package day23

import assertEquals
import println
import readInput

fun List<String>.parse(): List<Pair<String, String>> = map { it.split("-").let { it.first() to it.last() } }

fun part1(edges: List<Pair<String, String>>): Int {
    println(edges.joinToString("\n"))

    return edges.size
}

fun part2(edges: List<Pair<String, String>>): Int {
    return edges.size
}

fun main() {
    run {
        val edges = readInput("Day23_test01").parse()

        run {
            val expected = -1
            val actual = part1(edges)
            assertEquals(expected, actual)
        }

        run {
            val expected = -1
            val actual = part2(edges)
            assertEquals(expected, actual)
        }
    }

    run {
        val edges = readInput("Day23").parse()
        part1(edges).println()
        part2(edges).println()
    }
}
