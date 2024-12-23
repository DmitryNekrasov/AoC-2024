package day21

import assertEquals
import println
import readInput
import shouldNotReachHere
import java.util.LinkedList
import java.util.Queue
import kotlin.math.abs

const val A = 10

const val deltaA = 0
const val deltaUP = 1
const val deltaDOWN = 2
const val deltaLEFT = 3
const val deltaRIGHT = 4

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

val numpad = listOf(
    "789",
    "456",
    "123",
    "#0A"
)

val arrows = listOf(
    "#uA",
    "ldr"
)

val Char.digit: Int
    get() = if (this == 'A') A else digitToInt()

fun part1(input: List<String>): Int {
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

    for (i in numpad.indices) {
        for (j in numpad.first().indices) {
            if (numpad[i][j] == '#') continue
            val current = numpadVertices[numpad[i][j].digit]!!
            if (i - 1 >= 0) {
                val up = numpadVertices[numpad[i - 1][j].digit]!!
                graph.add(current.up, up.up)
            }
            if (i + 1 < numpad.size && numpad[i + 1][j] != '#') {
                val down = numpadVertices[numpad[i + 1][j].digit]!!
                graph.add(current.down, down.down)
            }
            if (j - 1 >= 0 && numpad[i][j - 1] != '#') {
                val left = numpadVertices[numpad[i][j - 1].digit]!!
                graph.add(current.left, left.left)
            }
            if (j + 1 < numpad.first().length) {
                val right = numpadVertices[numpad[i][j + 1].digit]!!
                graph.add(current.right, right.right)
            }
        }
    }

    println("graph = $graph")

    return input.sumOf { code ->
        code.substring(0..<code.lastIndex).toInt() * "A$code".map { it.digit }
            .zipWithNext().sumOf { (start, end) ->
                graph.distance(numpadVertices[start]!!.a, numpadVertices[end]!!.a)
            }
    }
}

fun List<String>.getCoordinates(c: Char): Pair<Int, Int> {
    for (i in indices) {
        for (j in first().indices) {
            if (this[i][j] == c) {
                return i to j
            }
        }
    }
    shouldNotReachHere()
}

const val UP = 'u'
const val DOWN = 'd'
const val LEFT = 'l'
const val RIGHT = 'r'

fun List<String>.generateAllPossiblePaths(start: Pair<Int, Int>, end: Pair<Int, Int>, distance: Int): List<String> {
    val n = size
    val m = first().length
    val visited = Array(n) { BooleanArray(m) }
    val result = mutableListOf<String>()

    fun dfs(i: Int, j: Int, currentDistance: Int = 0, currentPath: String = "") {
        if (i !in 0..<n || j !in 0..<m || visited[i][j] || this[i][j] == '#') return
        if (currentDistance == distance && i to j == end) result += currentPath + "A"
        visited[i][j] = true
        dfs(i - 1, j, currentDistance + 1, "$currentPath$UP")
        dfs(i + 1, j, currentDistance + 1, "$currentPath$DOWN")
        dfs(i, j - 1, currentDistance + 1, "$currentPath$LEFT")
        dfs(i, j + 1, currentDistance + 1, "$currentPath$RIGHT")
        visited[i][j] = false
    }

    dfs(start.first, start.second)

    return result
}

infix fun Pair<Int, Int>.manhattanDistance(rhs: Pair<Int, Int>) = abs(first - rhs.first) + abs(second - rhs.second)

fun String.prevCodesOn(keyboard: List<String>): List<List<String>> {
    val coordinates = "A$this".map { keyboard.getCoordinates(it) }
    return coordinates.zipWithNext().map { (from, to) ->
        keyboard.generateAllPossiblePaths(from, to, from manhattanDistance to)
    }
}

fun part2(input: List<String>): Long {
    val maxDepth = 3

    val cache = HashMap<Pair<String, Int>, Long>()

    fun solve(code: String, depth: Int = 0): Long {
        if (depth == maxDepth) return code.length.toLong()
        return cache.getOrPut(code to depth) {
            code.prevCodesOn(if (depth == 0) numpad else arrows).sumOf { codes ->
                codes.minOf { code ->
                    solve(code, depth + 1)
                }
            }
        }
    }

    for (code in input) {
        val result = solve(code)
        println("Code: $code, result = $result")
    }

    return input.sumOf { code ->
        code.substring(0..<code.lastIndex).toLong() * solve(code)
    }
}

fun main() {
    "<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A".count { it == 'A' }.also { println("test1: $it") }
    "<v<A>>^AAAvA^A<vA<AA>>^AvAA<^A>A<v<A>A>^AAAvA<^A>A<vA>^A<A>A".count { it == 'A' }.also { println("test2: $it") }
    "<v<A>>^A<vA<A>>^AAvAA<^A>A<v<A>>^AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A".count { it == 'A' }.also { println("test3: $it") }
    "<v<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^A<A>A<vA>^A<A>A<v<A>A>^AAvA<^A>A".count { it == 'A' }.also { println("test4: $it") }
    "<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A".count { it == 'A' }.also { println("test5: $it") }

    run {
        val input = readInput("Day21_test01")

        run {
            val expected = 126384
            val actual = part1(input)
            assertEquals(expected, actual)
        }

        part2(input).println()
    }

    run {
        val input = readInput("Day21")

        run {
            val expected = 184716
            val actual = part1(input)
            assertEquals(expected, actual)
        }

        part2(input).println()
    }
}
