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

fun part2(binary: Map<String, List<String>>): String {
    val graph = HashMap<String, MutableList<String>>()
    val operators = HashMap<String, String>()
    for ((to, from) in binary) {
        val (lhs, operator, rhs) = from
        graph.add(lhs, to)
        graph.add(rhs, to)
        operators[to] = operator
    }

    fun checkBit(number: Int, carry: String): String {
        val x = "x" + if (number <= 9) "0$number" else "$number"
        val y = "y" + if (number <= 9) "0$number" else "$number"

        val xGates = graph[x]!!.also { if (it.size != 2) println("ERROR! $x doesn't have 2 gates") }
        val yGates = graph[y]!!.also { if (it.size != 2) println("ERROR! $y doesn't have 2 gates") }

        val (xXOR, xAND) = xGates.partition { operators[it] == "XOR" }.toList().map { it.first() }
        val (yXOR, yAND) = yGates.partition { operators[it] == "XOR" }.toList().map { it.first() }

        if (xXOR != yXOR) println("ERROR! $x and $y have different XORs")
        if (xAND != yAND) println("ERROR! $x and $y have different ANDs")

        val firstXor = xXOR
        val firstAnd = xAND

        val firstXorGates =
            graph[firstXor]!!.also { if (it.size != 2) println("ERROR! $firstXor doesn't have 2 gates") }
        val carryGates = graph[carry]?.also { if (it.size != 2) println("Error! $carry doesn't have 2 gates") } ?: listOf("Invalid")

        if (firstXorGates.size != 2 && carryGates.size != 2) {
            println("I don't know what to do")
        }

        if (firstXorGates.size == 1) {
            println("REPORT: ($firstXor - ${firstXorGates.first()})")
        }
        if (carryGates.size == 1) {
            println("REPORT: ($carry - ${carryGates.first()})")
        }

        val (firstXorXor, firstXorAnd) = if (firstXorGates.size == 2) firstXorGates.partition { operators[it] == "XOR" }
            .toList().map { it.first() } else listOf("Invalid", "Invalid")
        val (carryXor, carryAnd) = if (carryGates.size == 2) carryGates.partition { operators[it] == "XOR" }
            .toList().map { it.first() } else listOf("InvalidCarry", "InvalidCarry")

        if (firstXorXor != carryXor) println("ERROR! $firstXor and $carry have different XORs")
        if (firstXorAnd != carryAnd) println("ERROR! $firstXor and $carry have different ANDs")

        val secondXor: String
        val secondAnd: String
        if (firstXorGates.map { operators[it]!! }.sorted() == listOf("AND", "XOR")) {
            secondXor = firstXorXor
            secondAnd = firstXorAnd
        } else {
            secondXor = carryXor
            secondAnd = carryAnd
        }

        if (!secondXor.startsWith("z")) {
            println("REPORT: ($firstXor - $secondXor)")
        }
        if (secondAnd.startsWith("z")) {
            println("REPORT: ($carry - $secondAnd)")
        }

        val firstAndGates = graph[firstAnd] ?: listOf("Invalid")
        val secondAndGates = graph[secondAnd] ?: listOf("Invalid")

        val firstAndOr = if (firstAndGates.size == 1) firstAndGates.first() else firstXorGates.first()
        val secondAndOr = secondAndGates.first()
        if (firstAndOr != secondAndOr) {
            println("Error! Different OR! for $firstAnd and $secondAnd")
        }

        if (firstAndGates.size != 1) {
            println("Error! $firstAnd doesn't have 1 gates")
        }
        if (secondAndGates.size != 1) {
            println("Error! $secondAnd doesn't have 1 gates")
        }

        println("x = $x")
        println("y = $y")

        return (if (firstAndOr != "Invalid") firstAndOr else secondAndOr).let {
            if (it.startsWith("z")) {
                println("REPORT! $it")
                firstXorXor
            } else {
                it
            }
        }
    }

    var carry = "rvh"
    for (bit in 1..44) {
        carry = checkBit(bit, carry)
    }

    val ans = listOf("z31", "jdr", "dwp", "kfm", "z08", "ffj", "z22", "gjh")
    return ans.sorted().joinToString(",")
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
        part2(binary).println()
    }
}
