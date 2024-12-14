package day14

import assertEquals
import println
import readInput

fun String.parse() = this.split(" ").flatMap { it.substring(2).split(",").map(String::toInt) }

fun List<String>.parse() = this.map(String::parse)

const val TIME = 100

fun part1(input: List<List<Int>>, n: Int, m: Int): Int {
    val finalPoints = input.map { (x, y, dx, dy) -> (x + dx * TIME).mod(n) to (y + dy * TIME).mod(m) }
    return finalPoints.count { (x, y) -> x < n / 2 && y < m / 2 } *
            finalPoints.count { (x, y) -> x < n / 2 && y > m / 2 } *
            finalPoints.count { (x, y) -> x > n / 2 && y < m / 2 } *
            finalPoints.count { (x, y) -> x > n / 2 && y > m / 2 }
}

fun part2(input: List<List<Int>>, n: Int, m: Int): Int {
    return input.size
}

fun main() {
    run {
        val input = readInput("Day14_test01").parse()
        val n = 11
        val m = 7

        run {
            val expected = 12
            val actual = part1(input, n, m)
            assertEquals(expected, actual)
        }

        run {
            part2(input, n, m)
        }
    }

    run {
        val input = readInput("Day14").parse()
        val n = 101
        val m = 103
        part1(input, n, m).println()
        part2(input, n, m)
    }
}
