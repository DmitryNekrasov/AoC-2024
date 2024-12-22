package day22

import assertEquals
import println
import readInput

const val MOD = 16777216L

fun Long.mixInto(other: Long) = this xor other

fun Long.prune() = this % MOD

fun Long.nextSecretNumber(): Long {
    val step1 = (this * 64).mixInto(this).prune()
    val step2 = (step1 / 32).mixInto(step1).prune()
    val step3 = (step2 * 2048).mixInto(step2).prune()
    return step3
}

fun part1(nums: List<Long>): Long {
    return nums.sumOf { num -> (0..<2000).fold(num) { acc, _ -> acc.nextSecretNumber() } }
}

fun part2(input: List<Long>): Int {
    return input.size
}

fun main() {
    run {
        val input = readInput("Day22_test01").map(String::toLong)

        run {
            val expected = 37327623L
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
        val input = readInput("Day22").map(String::toLong)
        part1(input).println()
        part2(input).println()
    }
}
