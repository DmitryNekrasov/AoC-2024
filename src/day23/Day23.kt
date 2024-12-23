package day23

import assertEquals
import println
import readInput

fun List<String>.parse(): List<Pair<String, String>> = map { it.split("-").let { it.first() to it.last() } }

fun HashMap<String, MutableSet<String>>.add(from: String, to: String) {
    getOrPut(from) { mutableSetOf() } += to
}

fun part1(edges: List<Pair<String, String>>): Int {
    val graph = HashMap<String, MutableSet<String>>()
    for ((from, to) in edges) {
        graph.add(from, to)
        graph.add(to, from)
    }

    val triangles = HashSet<List<String>>()
    for ((from, neighbours) in graph) {
        if (from.startsWith('t')) {
            for (u in neighbours) {
                for (v in neighbours) {
                    if (graph[v]?.contains(u) == true) {
                        triangles += listOf(from, u, v).sorted()
                    }
                }
            }
        }
    }

    return triangles.size
}

fun part2(edges: List<Pair<String, String>>): Int {
    return edges.size
}

fun main() {
    run {
        val edges = readInput("Day23_test01").parse()

        run {
            val expected = 7
            val actual = part1(edges)
            assertEquals(expected, actual)
        }

        run {
            val expected = -1
            val actual = part2(edges)
            assertEquals(expected, actual)
        }
    }

    run {
        val edges = readInput("Day23").parse()
        part1(edges).println()
        part2(edges).println()
    }
}
