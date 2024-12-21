package day21

import assertEquals
import println
import readInput

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

        return Vertex(up.up, down.down, left.left, right.right, a.a)
    }

    val numpadVertices = (0..A).associateWith { buildVertex(depth, it.toLong()) }
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

    println(graph)

    return input.size
}

fun part2(input: List<List<Int>>): Int {
    return input.size
}

fun main() {
    run {
        val input = readInput("Day21_test01").parse()

        run {
            val expected = 126384L
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
        val input = readInput("Day21").parse()
        part1(input).println()
        part2(input).println()
    }
}
