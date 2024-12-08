package day08

import assertEquals
import println
import readInput

operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) = first - other.first to second - other.second

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second

fun part1(input: List<String>): Int {
    fun List<String>.getCoordinates(): Map<Char, List<Pair<Int, Int>>> {
        val result = HashMap<Char, MutableList<Pair<Int, Int>>>()
        for (i in indices) {
            for (j in first().indices) {
                if (this[i][j] != '.') {
                    result.getOrPut(this[i][j]) { mutableListOf() } += i to j
                }
            }
        }
        return result
    }

    infix fun Pair<Int, Int>.antinode(other: Pair<Int, Int>): List<Pair<Int, Int>> {
        val diff = other - this
        return listOf(this - diff, other + diff)
    }

    val n = input.size
    val m = input.first().length

    fun Pair<Int, Int>.inRange() = first in 0..<n && second in 0..<m

    fun List<Pair<Int, Int>>.calculateAntinodes(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (i in 0..<lastIndex) {
            for (j in (i + 1)..lastIndex) {
                result += (this[j] antinode this[i]).filter(Pair<Int, Int>::inRange)
            }
        }
        return result
    }

    val antinodes = HashSet<Pair<Int, Int>>()
    for ((_, coordinates) in input.getCoordinates()) {
        antinodes += coordinates.calculateAntinodes()
    }

    return antinodes.size
}

fun part2(input: List<String>): Int {
    return input.size
}

fun main() {
    run {
        val input = readInput("Day08_test01")

        run {
            val expected = 14
            val actual = part1(input)
            assertEquals(expected, actual)
        }

        run {
            val expected = 34
            val actual = part2(input)
            assertEquals(expected, actual)
        }
    }

    run {
        val input = readInput("Day08")
        part1(input).println()
        part2(input).println()
    }
}
