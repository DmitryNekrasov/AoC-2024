package day21

import assertEquals
import println
import readInput
import shouldNotReachHere
import java.util.LinkedList
import java.util.Queue

const val EMPTY = -1
const val A = 10

const val deltaA = 0
const val deltaUP = 1
const val deltaDOWN = 2
const val deltaLEFT = 3
const val deltaRIGHT = 4

fun List<String>.parse() = map { it.toCharArray().map { c -> if (c == 'A') A else c.digitToInt() } }

data class Vertex(val up: Long, val down: Long, val left: Long, val right: Long, val a: Long)

fun HashMap<Long, MutableList<Long>>.add(from: Long, to: Long) {
    getOrPut(from) { mutableListOf() } += to
}

fun HashMap<Long, MutableList<Long>>.distance(start: Long, end: Long): Int {
    val queue: Queue<Pair<Long, Int>> = LinkedList()
    queue.offer(start to 1)
    val visited = mutableSetOf(start)
    while (queue.isNotEmpty()) {
        val (from, distance) = queue.poll()
        if (from == end) return distance
        this[from]?.also { neighbors ->
            for (to in neighbors) {
                if (to !in visited) {
                    visited += to
                    queue.offer(to to distance + 1)
                }
            }
        }
    }
    shouldNotReachHere()
}

fun List<Int>.asInt(): Int {
    var result = 0
    for (i in 0..<lastIndex) {
        result = result * 10 + this[i]
    }
    return result
}

fun part1(input: List<List<Int>>): Int {
    val depth = 2

    val graph = HashMap<Long, MutableList<Long>>()

    fun buildVertex(depth: Int, id: Long): Vertex {
        if (depth == 0) {
            return Vertex(id, id, id, id, id)
        }

        val a = buildVertex(depth - 1, id * 5 + deltaA)
        val up = buildVertex(depth - 1, id * 5 + deltaUP)
        val down = buildVertex(depth - 1, id * 5 + deltaDOWN)
        val left = buildVertex(depth - 1, id * 5 + deltaLEFT)
        val right = buildVertex(depth - 1, id * 5 + deltaRIGHT)

        graph.add(left.right, down.right)
        graph.add(down.left, left.left)
        graph.add(down.up, up.up)
        graph.add(up.down, down.down)
        graph.add(down.right, right.right)
        graph.add(right.left, down.left)
        graph.add(up.right, a.right)
        graph.add(a.left, up.left)
        graph.add(right.up, a.up)
        graph.add(a.down, right.down)

        return Vertex(up.a, down.a, left.a, right.a, a.a)
    }

    val numpadVertices = (0..A).associateWith { buildVertex(depth, it.toLong()) }
    println("numpadVertices = $numpadVertices")

    val numpad = listOf(
        listOf(7, 8, 9),
        listOf(4, 5, 6),
        listOf(1, 2, 3),
        listOf(EMPTY, 0, A)
    )

    for (i in numpad.indices) {
        for (j in numpad.first().indices) {
            if (numpad[i][j] == EMPTY) continue
            val current = numpadVertices[numpad[i][j]]!!
            if (i - 1 >= 0) {
                val up = numpadVertices[numpad[i - 1][j]]!!
                graph.add(current.up, up.up)
            }
            if (i + 1 < numpad.size && numpad[i + 1][j] != EMPTY) {
                val down = numpadVertices[numpad[i + 1][j]]!!
                graph.add(current.down, down.down)
            }
            if (j - 1 >= 0 && numpad[i][j - 1] != EMPTY) {
                val left = numpadVertices[numpad[i][j - 1]]!!
                graph.add(current.left, left.left)
            }
            if (j + 1 < numpad.first().size) {
                val right = numpadVertices[numpad[i][j + 1]]!!
                graph.add(current.right, right.right)
            }
        }
    }

    println("graph = $graph")

    return input.sumOf { code ->
        code.asInt() * (listOf(A) + code).zipWithNext().sumOf {
                (start, end) -> graph.distance(numpadVertices[start]!!.a, numpadVertices[end]!!.a)
        }
    }
}

fun List<List<Int>>.getCoordinates(num: Int): Pair<Int, Int> {
    for (i in indices) {
        for (j in first().indices) {
            if (this[i][j] == num) {
                return i to j
            }
        }
    }
    shouldNotReachHere()
}

fun part2(input: List<List<Int>>): Int {
    val numpad = listOf(
        listOf(7, 8, 9),
        listOf(4, 5, 6),
        listOf(1, 2, 3),
        listOf(EMPTY, 0, A)
    )

    for (code in input) {
        val coordinates = (listOf(A) + code).map { numpad.getCoordinates(it) }

        println("coordinates = $coordinates")
    }

    return input.size
}

fun main() {
    "<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A".count { it == 'A' }.also { println("test1: $it") }
    "<v<A>>^AAAvA^A<vA<AA>>^AvAA<^A>A<v<A>A>^AAAvA<^A>A<vA>^A<A>A".count { it == 'A' }.also { println("test2: $it") }
    "<v<A>>^A<vA<A>>^AAvAA<^A>A<v<A>>^AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A".count { it == 'A' }.also { println("test3: $it") }
    "<v<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^A<A>A<vA>^A<A>A<v<A>A>^AAvA<^A>A".count { it == 'A' }.also { println("test4: $it") }
    "<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A".count { it == 'A' }.also { println("test5: $it") }

    run {
        val input = readInput("Day21_test01").parse()

        run {
            val expected = 126384
            val actual = part1(input)
            assertEquals(expected, actual)
        }

        part2(input).println()
    }

    run {
        val input = readInput("Day21").parse()
        part1(input).println()
        part2(input).println()
    }
}
