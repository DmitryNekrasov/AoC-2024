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

fun solve(nums: List<Long>, step: Int): Long {
    val cache = HashMap<Pair<Long, Int>, Long>()

    fun Long.splitNumber(step: Int): Long {
        if (step == 0) return 1
        return cache.getOrPut(this to step) {
            when {
                this == 0L -> 1L.splitNumber(step - 1)
                hasAnEvenNumberOfDigits -> split().let { (left, right) -> left.splitNumber(step - 1) + right.splitNumber(step - 1) }
                else ->(this * 2024L).splitNumber(step - 1)
            }
        }
    }

    return nums.sumOf { it.splitNumber(step) }
}

fun main() {
    run {
        val input = readInput("Day11_test01").first().split(" ").map(String::toLong)

        run {
            val expected = 55312L
            val actual = solve(input, 25)
            assertEquals(expected, actual)
        }

        run {
            solve(input, 75).println()
        }
    }

    run {
        val input = readInput("Day11").first().split(" ").map(String::toLong)
        solve(input, 25).println()
        solve(input, 75).println()
    }
}
