package day07

import assertEquals
import println
import readInput
import java.math.BigInteger

fun String.parseLine(): Pair<BigInteger, List<BigInteger>> {
    val (target, values) = split(":")
    return BigInteger(target) to values.trim().split(" ").map { BigInteger(it) }
}

fun part1(input: List<String>): BigInteger {
    fun check(target: BigInteger, values: List<BigInteger>, current: BigInteger = values.first(), index: Int = 1): Boolean {
        if (index == values.size) return current == target
        return check(target, values, current + values[index], index + 1) || check(target, values, current * values[index], index + 1)
    }

    return input.map(String::parseLine).filter { (target, values) -> check(target, values) }.sumOf { it.first }
}

fun part2(input: List<String>): BigInteger {
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

    infix fun BigInteger.concat(other: BigInteger) = BigInteger("${this}${other}")

    fun perform(values: List<BigInteger>, operators: List<Char>): BigInteger {
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
            val expected = BigInteger.valueOf(3749)
            val actual = part1(input)
            assertEquals(expected, actual)
        }

        run {
            val expected = BigInteger.valueOf(11387)
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
