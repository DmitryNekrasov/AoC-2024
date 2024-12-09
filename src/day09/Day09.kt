package day09

import assertEquals
import println
import readInput

fun Int.repeat(n: Int) = List(n) { this }

fun part1(input: String): Long {
    val blocks = input.withIndex().fold(mutableListOf<Int>()) { acc, p ->
        acc.also {
            acc += (if ((p.index and 1) == 0) p.index / 2 else -1).repeat(p.value.code - '0'.code)
        }
    }

    var p1 = 0
    var p2 = blocks.lastIndex
    while (p1 < p2) {
        if (blocks[p1] != -1) {
            p1++
            continue
        }
        if (blocks[p2] == -1) {
            p2--
            continue
        }
        blocks[p1] = blocks[p2]
        blocks[p2] = -1
        p1++
        p2--
    }

    return blocks.filter { it != -1 }.withIndex().sumOf { (index, value) -> index * value.toLong() }
}

fun part2(input: String): Long {
    return 0L
}

fun main() {
    run {
        val input = readInput("Day09_test01").first()

        run {
            val expected = 1928L
            val actual = part1(input)
            assertEquals(expected, actual)
        }

        run {
            val expected = 2858L
            val actual = part2(input)
            assertEquals(expected, actual)
        }
    }

    run {
        val input = readInput("Day09").first()
        part1(input).println()
        part2(input).println()
    }
}
