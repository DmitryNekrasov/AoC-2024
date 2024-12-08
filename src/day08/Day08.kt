package day08

import assertEquals
import println
import readInput

operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) = first - other.first to second - other.second

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second

fun solve(input: List<String>, antinode: Pair<Int, Int>.(Pair<Int, Int>) -> List<Pair<Int, Int>>): Int {
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

    fun List<Pair<Int, Int>>.calculateAntinodes(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (i in 0..<lastIndex) {
            for (j in (i + 1)..lastIndex) {
                result += this[j].antinode(this[i])
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

fun Pair<Int, Int>.inRange(grid: List<String>) = first in 0..<grid.size && second in 0..<grid.first().length

fun part1(input: List<String>): Int {
    fun Pair<Int, Int>.antinode(other: Pair<Int, Int>): List<Pair<Int, Int>> {
        val diff = other - this
        return listOf(this - diff, other + diff).filter { it.inRange(input) }
    }

    return solve(input, Pair<Int, Int>::antinode)
}

fun part2(input: List<String>): Int {
    fun Pair<Int, Int>.antinode(other: Pair<Int, Int>): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        val diff = other - this
        var current = this
        while (current.inRange(input)) {
            result += current
            current -= diff
        }
        current = other
        while (current.inRange(input)) {
            result += current
            current += diff
        }
        return result
    }

    return solve(input, Pair<Int, Int>::antinode)
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
