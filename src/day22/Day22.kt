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

const val MAX_SIZE = 2000

fun part1(nums: List<Long>): Long {
    return nums.sumOf { num -> (0..<2000).fold(num) { acc, _ -> acc.nextSecretNumber() } }
}

fun part2(nums: List<Long>): Int {
    val changes = HashMap<List<Int>, Int>()
    for (num in nums) {
        val changesForNum = HashMap<List<Int>, Int>()
        val secretNumbers = (0..<MAX_SIZE).runningFold(num) { acc, _ -> acc.nextSecretNumber() }
        val prices = secretNumbers.map { (it % 10).toInt() }
        val diffs = prices.zipWithNext().map { (a, b) -> b - a }
        for ((index, change) in diffs.windowed(4).withIndex()) {
            if (change !in changesForNum) {
                changesForNum[change] = prices[index + 4]
            }
        }
        for ((change, maxPrice) in changesForNum) {
            changes[change] = (changes[change] ?: 0) + maxPrice
        }
    }

    return changes.values.max()
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
            part2(input).println()
        }
    }

    run {
        val input = readInput("Day22").map(String::toLong)
        part1(input).println()
        part2(input).println()
    }
}
