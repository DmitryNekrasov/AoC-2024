package day07

import assertEquals
import println
import readInput

fun String.parseLine(): Pair<Long, List<Long>> {
    val (target, values) = split(":")
    return target.toLong() to values.trim().split(" ").map { it.toLong() }
}

fun part1(input: List<String>): Long {
    fun check(target: Long, values: List<Long>, current: Long = values.first(), index: Int = 1): Boolean {
        if (index == values.size) return current == target
        return check(target, values, current + values[index], index + 1) || check(target, values, current * values[index], index + 1)
    }

    return input.map(String::parseLine).filter { (target, values) -> check(target, values) }.sumOf { it.first }
}

fun part2(input: List<String>): Long {
    val cache = HashMap<Int, List<List<Char>>>()

    fun generateOperators(n: Int, current: MutableList<Char> = mutableListOf()): List<List<Char>> {
        if (n == 0) return listOf(current.toList())
        val result = mutableListOf<List<Char>>()

        current += '+'
        result += generateOperators(n - 1, current)
        current.removeAt(current.lastIndex)

        current += '*'
        result += generateOperators(n - 1, current)
        current.removeAt(current.lastIndex)

        current += '|'
        result += generateOperators(n - 1, current)
        current.removeAt(current.lastIndex)

        return result
    }

    infix fun Long.concat(other: Long) = "${this}${other}".toLong()

    fun perform(values: List<Long>, operators: List<Char>): Long {
        var result = values.first()
        for ((operator, value) in operators zip values.slice(1..values.lastIndex)) {
            when (operator) {
                '+' -> result += value
                '*' -> result *= value
                '|' -> result = result concat value
                else -> throw UnsupportedOperationException("Unsupported operation $operator")
            }
        }
        return result
    }

    return input.map(String::parseLine).filter { (target, values) ->
        cache.getOrPut(values.size - 1) { generateOperators(values.size - 1) }
            .any { operators -> perform(values, operators) == target }
    }.sumOf { it.first }
}

fun main() {
    run {
        val input = readInput("Day07_test01")

        run {
            val expected = 3749L
            val actual = part1(input)
            assertEquals(expected, actual)
        }

        run {
            val expected = 11387L
            val actual = part2(input)
            assertEquals(expected, actual)
        }
    }

    run {
        val input = readInput("Day07")
        part1(input).println()
        part2(input).println()
    }
}
