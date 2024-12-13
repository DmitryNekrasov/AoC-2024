package day13

import assertEquals
import println
import readInput
import kotlin.collections.plusAssign

data class Equation(val aX: Int, val aY: Int, val bX: Int, val bY: Int, val targetX: Int, val targetY: Int)

fun List<String>.parseEquation(): Equation {
    val list = this.flatMap { line ->
        line.split(":").last().trim().split(", ").map { it.substring(2) }.map(String::toInt)
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

fun part1(equations: List<Equation>): Int {
    println(equations.joinToString("\n"))

    return equations.size
}

fun part2(equations: List<Equation>): Int {
    return equations.size
}

fun main() {
    run {
        val input = readInput("Day13_test01").parse()

        run {
            val expected = 480
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
