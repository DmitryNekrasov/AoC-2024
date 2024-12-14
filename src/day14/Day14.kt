package day14

import assertEquals
import println
import readInput

fun String.parse() = this.split(" ").flatMap { it.substring(2).split(",").map(String::toInt) }

fun List<String>.parse() = this.map(String::parse)

fun part1(input: List<List<Int>>, N: Int, M: Int): Int {
    println(input.joinToString("\n"))

    return input.size
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
