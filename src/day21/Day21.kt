package day21

import assertEquals
import println
import readInput

data class Vertex(val up: Long, val down: Long, val left: Long, val right: Long)

fun HashMap<Long, MutableList<Long>>.add(from: Long, to: Long) {
    getOrPut(from) { mutableListOf() } += to
}

fun part1(input: List<String>): Int {
    val graph = HashMap<Long, MutableList<Long>>()

    fun buildVertex(depth: Int, id: Long): Vertex {
        if (depth == 0) {
            return Vertex(id, id, id, id)
        }

        val a = buildVertex(depth - 1, id * 5)
        val up = buildVertex(depth - 1, id * 5 + 1)
        val down = buildVertex(depth - 1, id * 5 + 2)
        val left = buildVertex(depth - 1, id * 5 + 3)
        val right = buildVertex(depth - 1, id * 5 + 4)

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

        return Vertex(up.up, down.down, left.left, right.right)
    }

    return input.size
}

fun part2(input: List<String>): Int {
    return input.size
}

fun main() {
    run {
        val input = readInput("Day21_test01")

        run {
            val expected = -1
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
        val input = readInput("Day21")
        part1(input).println()
        part2(input).println()
    }
}
