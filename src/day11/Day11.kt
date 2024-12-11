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
    for (num in nums) {
        println("$num: ${num.hasAnEvenNumberOfDigits}")
        if (num.hasAnEvenNumberOfDigits) {
            val (left, right) = num.split()
            println("left = $left, right = $right")
        }
    }

    return nums.size
}

fun part2(input: List<Long>): Int {
    return input.size
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
