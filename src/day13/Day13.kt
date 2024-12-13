package day13

import assertEquals
import println
import readInput

data class Equation(val aX: Long, val aY: Long, val bX: Long, val bY: Long, val targetX: Long, val targetY: Long) {
    fun solve(): Pair<Long, Long> {
        val m = (targetY - aY * targetX / aX.toDouble()) / (bY - aY * bX / aX.toDouble())
        val n = (targetX - m * bX) / aX
        return (n + 0.1).toLong() to (m + 0.1).toLong()
    }

    fun verify(n: Long, m: Long): Boolean {
        return n * aX + m * bX == targetX && n * aY + m * bY == targetY
    }
}

fun List<String>.parseEquation(): Equation {
    val list = this.flatMap { line ->
        line.split(": ").last().split(", ").map { it.substring(2) }.map(String::toLong)
    }
    return Equation(list[0], list[1], list[2], list[3], list[4], list[5])
}

fun List<String>.parse(): List<Equation> {
    return this.fold(mutableListOf<MutableList<String>>().apply { add(mutableListOf()) }) { acc, value ->
        acc.apply {
            when (value) {
                "" -> this += mutableListOf<String>()
                else -> last() += value
            }
        }
    }.map(List<String>::parseEquation)
}

fun part1(equations: List<Equation>): Long {
    var result = 0L
    for (equation in equations) {
        val (n, m) = equation.solve()
        if (equation.verify(n, m)) {
            result += 3 * n + m
        }
    }

    return result
}

fun part2(equations: List<Equation>): Int {
    return equations.size
}

fun main() {
    run {
        val input = readInput("Day13_test01").parse()

        run {
            val expected = 480L
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
        val input = readInput("Day13").parse()
        part1(input).println()
        part2(input).println()
    }
}
