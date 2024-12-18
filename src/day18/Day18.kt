package day18

import assertEquals
import println
import readInput

fun List<String>.parse() = map { it.split(",").map(String::toInt).let { (x, y) -> x to y } }

fun part1(n: Int, limit: Int, points: List<Pair<Int, Int>>): Int {
    println(points)

    return points.size
}

fun part2(points: List<Pair<Int, Int>>): Int {
    return points.size
}

fun main() {
    run {
        val n = 6
        val limit = 12
        val points = readInput("Day18_test01").parse()

        run {
            val expected = 22
            val actual = part1(n, limit, points)
            assertEquals(expected, actual)
        }

        run {
            val expected = -1
            val actual = part2(points)
            assertEquals(expected, actual)
        }
    }

    run {
        val n = 70
        val limit = 1024
        val points = readInput("Day18").parse()

        part1(n, limit, points).println()
        part2(points).println()
    }
}
