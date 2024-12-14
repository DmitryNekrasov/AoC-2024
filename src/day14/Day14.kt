package day14

import assertEquals
import println
import readInput

fun String.parse() = this.split(" ").flatMap { it.substring(2).split(",").map(String::toInt) }

fun List<String>.parse() = this.map(String::parse)

const val TIME = 100

fun part1(input: List<List<Int>>, N: Int, M: Int): Int {
    val finalPoints = input.map { (x, y, dx, dy) -> (x + dx * TIME).mod(N) to (y + dy * TIME).mod(M) }
    return finalPoints.count { (x, y) -> x < N / 2 && y < M / 2 } *
            finalPoints.count { (x, y) -> x < N / 2 && y > M / 2 } *
            finalPoints.count { (x, y) -> x > N / 2 && y < M / 2 } *
            finalPoints.count { (x, y) -> x > N / 2 && y > M / 2 }
}

fun part2(input: List<List<Int>>, N: Int, M: Int): Int {
    return input.size
}

fun main() {
    run {
        val input = readInput("Day14_test01").parse()
        val N = 11
        val M = 7

        run {
            val expected = 12
            val actual = part1(input, N, M)
            assertEquals(expected, actual)
        }

        run {
            val expected = -1
            val actual = part2(input, N, M)
            assertEquals(expected, actual)
        }
    }

    run {
        val input = readInput("Day14").parse()
        val N = 101
        val M = 103

        part1(input, N, M).println()
        part2(input, N, M).println()
    }
}
