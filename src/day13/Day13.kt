package day13

import assertEquals
import println
import readInput

data class Equation(val aX: Long, val aY: Long, val bX: Long, val bY: Long, val targetX: Long, val targetY: Long) {
    fun solve(addition: Long): Pair<Long, Long> {
        val m = ((targetY + addition) - aY * (targetX + addition) / aX.toDouble()) / (bY - aY * bX / aX.toDouble())
        val n = ((targetX + addition) - m * bX) / aX
        return (n + 0.1).toLong() to (m + 0.1).toLong()
    }

    fun verify(n: Long, m: Long, addition: Long): Boolean {
        return n * aX + m * bX == (targetX + addition) && n * aY + m * bY == (targetY + addition)
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

fun solve(equations: List<Equation>, addition: Long = 0L): Long {
    var result = 0L
    for (equation in equations) {
        val (n, m) = equation.solve(addition)
        if (equation.verify(n, m, addition)) {
            result += 3 * n + m
        }
    }
    return result
}

fun main() {
    run {
        val input = readInput("Day13_test01").parse()

        run {
            val expected = 480L
            val actual = solve(input)
            assertEquals(expected, actual)
        }

        run {
            solve(input, 10000000000000)
        }
    }

    run {
        val input = readInput("Day13").parse()
        solve(input).println()
        solve(input, 10000000000000).println()
    }
}
