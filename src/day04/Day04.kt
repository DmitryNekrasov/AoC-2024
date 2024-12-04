package day04

import assertEquals
import println
import readInput

const val xmas = "XMAS"

fun part1(input: List<String>): Int {
    val n = input.size
    val m = input.first().length

    fun generateDirections(): List<Pair<Int, Int>> {
        val steps = listOf(-1, 0, 1)
        val directions = mutableListOf<Pair<Int, Int>>()
        for (di in steps) {
            for (dj in steps) {
                if (di != 0 || dj != 0) {
                    directions.add(di to dj)
                }
            }
        }
        return directions
    }

    var result = 0
    val directions = generateDirections()
    for (i in 0..<n) {
        for (j in 0..<m) {
            val accumulator = IntArray(directions.size)
            for (k in xmas.indices) {
                for ((index, direction) in directions.withIndex()) {
                    val (di, dj) = direction
                    val newI = i + di * k
                    val newJ = j + dj * k
                    accumulator[index] += if (newI in 0..<n && newJ in 0..<m && input[newI][newJ] == xmas[k]) 1 else 0
                }
            }
            result += accumulator.count { it == xmas.length }
        }
    }

    return result
}

fun part2(input: List<String>): Int {
    return input.size
}

fun main() {
    run {
        val input = readInput("Day04_test01")

        run {
            val expected = 18
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
        val input = readInput("Day04")
        part1(input).println()
        part2(input).println()
    }
}
