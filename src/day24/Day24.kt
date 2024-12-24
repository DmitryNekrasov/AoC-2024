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

fun part1(unary: Map<String, Int>, binary: Map<String, List<String>>): Long {
    val nodes = HashMap<String, Node>()

    fun buildNode(name: String): Node {
        return unary[name]?.let { nodes.getOrPut(name) { Unary(it) } }
            ?: binary[name]?.let { nodes.getOrPut(name) { Binary(buildNode(it[0]), buildNode(it[2]), it[1]) } }
            ?: shouldNotReachHere()
    }

    val zNodes = binary.keys.filter { it.startsWith("z") }.map { it to buildNode(it) }
        .sortedByDescending { it.first }.map { it.second }

    return zNodes.fold(0L) { acc, node -> (acc shl 1) or node.perform().toLong() }
}

fun HashMap<String, MutableList<String>>.add(from: String, to: String) {
    getOrPut(from) { mutableListOf() } += to
}

fun part2(unary: Map<String, Int>, binary: Map<String, List<String>>): Int {
    val graph = HashMap<String, MutableList<String>>()
    val operators = HashMap<String, String>()
    for ((to, from) in binary) {
        val (lhs, operator, rhs) = from
        graph.add(lhs, to)
        graph.add(rhs, to)
        operators[to] = operator
    }

    println(graph.entries.sortedBy { it.key }
        .joinToString("\n") {
            it.key + (operators[it.key]?.let { "($it)" }
                ?: "") + " -> " + it.value.joinToString(", ") { it + "(" + operators[it] + ")" }
        })

    fun HashMap<String, MutableList<String>>.`all initial bits enter to XOR and AND`(): Boolean {
        return graph.filter { it.key.startsWith("x") || it.key.startsWith("y") }
            .map { it.value }
            .all {
                it.size == 2 &&
                        listOf(operators[it.first()]!!, operators[it.last()]!!).sorted() == listOf("AND", "XOR")
            }
    }

    println("All initial bits enter to XOR and AND: ${graph.`all initial bits enter to XOR and AND`()}")

    return unary.size
}

fun main() {
    run {
        val (unary, binary) = readInput("Day24_test01").parse()

        run {
            val expected = 2024L
            val actual = part1(unary, binary)
            assertEquals(expected, actual)
        }
    }

    run {
        val (unary, binary) = readInput("Day24").parse()
        part1(unary, binary).println()
        part2(unary, binary).println()
    }
}
