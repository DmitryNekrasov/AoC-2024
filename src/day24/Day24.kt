package day24

import assertEquals
import println
import readInput
import shouldNotReachHere

fun List<String>.parse(): Pair<Map<String, Int>, Map<String, List<String>>> {
    val pivot = indexOf("")
    return slice(0..<pivot).associate { it.split(": ").let { (s, v) -> s to v.toInt() } } to
            slice((pivot + 1)..lastIndex).associate { it.split(" -> ").let { (to, from) -> from to to.split(" ") } }
}

interface Node {
    fun perform(): Int
}

class Unary(val value: Int) : Node {
    override fun perform() = value
}

class Binary(val lhs: Node, val rhs: Node, val operation: String) : Node {
    override fun perform() = when (operation) {
        "AND" -> lhs.perform() and rhs.perform()
        "OR" -> lhs.perform() or rhs.perform()
        "XOR" -> lhs.perform() xor rhs.perform()
        else -> shouldNotReachHere()
    }
}

fun part1(unary: Map<String, Int>, binary: Map<String, List<String>>): Int {
    unary.entries.joinToString("\n").println()
    binary.entries.joinToString("\n").println()

    return unary.size
}

fun part2(unary: Map<String, Int>, binary: Map<String, List<String>>): Int {
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
