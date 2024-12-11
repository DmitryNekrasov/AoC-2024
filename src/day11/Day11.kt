package day11

import assertEquals
import println
import readInput

val Long.numberOfDigits: Int
    get() {
        if (this == 0L) return 1
        var n = this
        var count = 0
        while (n > 0L) {
            n /= 10
            count++
        }
        return count
    }

val Long.hasAnEvenNumberOfDigits: Boolean
    get() = (numberOfDigits and 1) == 0

fun Long.split(): List<Long> {
    var left = this
    var right = 0L
    var d = 1
    repeat(numberOfDigits / 2) {
        right += d * (left % 10)
        d *= 10
        left /= 10
    }
    return listOf(left, right)
}

fun part1(nums: List<Long>): Int {
    fun Long.splitNumber(step: Int): Int {
        if (step == 0) return 1
        return when {
            this == 0L -> 1L.splitNumber(step - 1)
            hasAnEvenNumberOfDigits -> split().let { (left, right) -> left.splitNumber(step - 1) + right.splitNumber(step - 1) }
            else ->(this * 2024L).splitNumber(step - 1)
        }
    }

    return nums.sumOf { it.splitNumber(25) }
}

fun part2(nums: List<Long>): Int {
    return 0
}

fun main() {
    run {
        val input = readInput("Day11_test01").first().split(" ").map(String::toLong)

        run {
            val expected = 55312
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
        val input = readInput("Day11").first().split(" ").map(String::toLong)
        part1(input).println()
        part2(input).println()
    }
}
