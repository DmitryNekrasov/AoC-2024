package day07

import assertEquals
import println
import readInput
import java.math.BigInteger

fun String.parseLine(): Pair<BigInteger, List<BigInteger>> {
    val (target, values) = split(":")
    return BigInteger(target) to values.trim().split(" ").map { BigInteger(it) }
}

fun check(target: BigInteger, values: List<BigInteger>, current: BigInteger = values.first(), index: Int = 1): Boolean {
    if (index == values.size) return current == target
    return check(target, values, current + values[index], index + 1) || check(target, values, current * values[index], index + 1)
}

fun part1(input: List<String>): BigInteger {
    return input.map(String::parseLine).filter { (target, values) -> check(target, values) }.sumOf { it.first }
}

fun part2(input: List<String>): Int {
    return input.size
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
            val expected = -1
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
