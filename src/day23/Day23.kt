package day23

import assertEquals
import println
import readInput
import shouldNotReachHere

fun List<String>.parse(): List<Pair<String, String>> = map { it.split("-").let { it.first() to it.last() } }

fun HashMap<String, MutableSet<String>>.add(from: String, to: String) {
    getOrPut(from) { mutableSetOf() } += to
}

fun List<Pair<String, String>>.toGraph(): HashMap<String, MutableSet<String>> {
    val graph = HashMap<String, MutableSet<String>>()
    for ((from, to) in this) {
        graph.add(from, to)
        graph.add(to, from)
    }
    return graph
}

fun part1(graph: HashMap<String, MutableSet<String>>): Int {
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

fun HashMap<String, MutableSet<String>>.findCliqueByBronKerbosch(result: MutableList<String>,
                                                                 r: Set<String> = mutableSetOf(),
                                                                 p: MutableSet<String> = keys.toMutableSet(),
                                                                 x: MutableSet<String> = mutableSetOf()) {
    if (p.isEmpty() && x.isEmpty()) {
        result += r.toList().sorted().joinToString(",")
    } else {
        val pivot = (p union x).maxByOrNull { this[it]?.size ?: 0 } ?: shouldNotReachHere()
        for (v in p - this[pivot]!!) {
            findCliqueByBronKerbosch(result, r union setOf(v), (p intersect this[v]!!).toMutableSet(), (x intersect this[v]!!).toMutableSet())
            p.remove(v)
            x.add(v)
        }
    }
}

fun part2(graph: HashMap<String, MutableSet<String>>): String {
    val result = mutableListOf<String>()
    graph.findCliqueByBronKerbosch(result)
    result.sortBy { it.length }
    return result.last()
}

fun main() {
    run {
        val graph = readInput("Day23_test01").parse().toGraph()

        run {
            val expected = 7
            val actual = part1(graph)
            assertEquals(expected, actual)
        }

        run {
            val expected = "co,de,ka,ta"
            val actual = part2(graph)
            assertEquals(expected, actual)
        }
    }

    run {
        val graph = readInput("Day23").parse().toGraph()
        val part1 = part1(graph)
        val start = System.nanoTime()
        val part2 = part2(graph)
        val end = System.nanoTime()

        part1.println()
        part2.println()
        println("time = ${(end.toDouble() - start) / 1_000_000} ms")
    }
}
