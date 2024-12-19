package day19

import assertEquals
import println
import readInput
import kotlin.math.min

fun List<String>.parse() = first().split(", ") to slice(2..lastIndex)

const val BASE = 283
const val MOD = 1_000_000_000_000_000 - 11

fun String.hashes(from: Int, to: Int): LongArray {
    val result = LongArray(to - from)
    result[0] = this[from].code.toLong()
    for (i in (from + 1)..<to) {
        result[i - from] = (result[i - from - 1] * BASE % MOD + this[i].code.toLong()) % MOD
    }
    return result
}

val String.hash: Long
    get() = hashes(0, length).last()

fun part1(patterns: List<String>, designs: List<String>): Int {
    val hashDict = patterns.groupBy { it.length }
        .mapValues { (_, fixedLenPatterns) -> fixedLenPatterns.map(String::hash).toSet() }

    val maxPatternLen = hashDict.keys.max()

    fun String.backtrack(from: Int = 0): Boolean {
        if (from == length) return true
        return hashes(from, min(from + maxPatternLen, length)).withIndex()
            .any { (index, hash) -> if (hash in hashDict[index + 1]!!) backtrack(from + index + 1) else false }
    }

    return designs.count { it.backtrack() }
}

fun part2(patterns: List<String>, designs: List<String>): Int {
    return patterns.size
}

fun main() {
    run {
        val (patterns, designs) = readInput("Day19_test01").parse()

        run {
            val expected = 6
            val actual = part1(patterns, designs)
            assertEquals(expected, actual)
        }

        run {
            val expected = -1
            val actual = part2(patterns, designs)
            assertEquals(expected, actual)
        }
    }

    run {
        val (patterns, designs) = readInput("Day19").parse()
        part1(patterns, designs).println()
        part2(patterns, designs).println()
    }
}
