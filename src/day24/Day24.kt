package day24

import assertEquals
import println
import readInput

fun List<String>.parse(): Pair<List<Pair<String, Int>>, List<Pair<String, List<String>>>> {
    val pivot = indexOf("")
    return slice(0..<pivot).map { it.split(": ").let { (s, v) -> s to v.toInt() } } to
            slice((pivot + 1)..lastIndex).map { it.split(" -> ").let { (to, from) -> from to to.split(" ") } }
}

fun part1(unary: List<Pair<String, Int>>, binary: List<Pair<String, List<String>>>): Int {
    unary.joinToString("\n").println()
    binary.joinToString("\n").println()

    return unary.size
}

fun part2(unary: List<Pair<String, Int>>, binary: List<Pair<String, List<String>>>): Int {
    return unary.size
}

fun main() {
    run {
        val (unary, binary) = readInput("Day24_test01").parse()

        run {
            val expected = 2024
            val actual = part1(unary, binary)
            assertEquals(expected, actual)
        }

        run {
            val expected = -1
            val actual = part2(unary, binary)
            assertEquals(expected, actual)
        }
    }

    run {
        val (unary, binary) = readInput("Day24").parse()
        part1(unary, binary).println()
        part2(unary, binary).println()
    }
}
