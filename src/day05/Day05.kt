package day05

import assertEquals
import println
import readInput

fun List<String>.parse(): Pair<HashMap<Int, MutableList<Int>>, List<List<Int>>> {
    fun List<String>.parseGraph(): HashMap<Int, MutableList<Int>> {
        val graph = HashMap<Int, MutableList<Int>>()
        for ((from, to) in this.map { it.split("|").map(String::toInt) }) {
            graph.getOrPut(from) { mutableListOf() } += to
        }
        return graph
    }

    fun List<String>.parseSequences() = this.map { it.split(",").map(String::toInt) }

    val (pageOrdering, updateString) = this.indexOf("")
        .let { index -> this.slice(0..<index) to this.slice((index + 1)..this.lastIndex) }

    return pageOrdering.parseGraph() to updateString.parseSequences()
}

fun part1(graph: HashMap<Int, MutableList<Int>>, sequences: List<List<Int>>): Int {
    return graph.size
}

fun part2(graph: HashMap<Int, MutableList<Int>>, sequences: List<List<Int>>): Int {
    return graph.size
}

fun main() {
    run {
        val (graph, sequences) = readInput("Day05_test01").parse()
        println(graph)
        println(sequences)

        run {
            val expected = -1
            val actual = part1(graph, sequences)
            assertEquals(expected, actual)
        }

        run {
            val expected = -1
            val actual = part2(graph, sequences)
            assertEquals(expected, actual)
        }
    }

    run {
        val (graph, sequences) = readInput("Day05").parse()
        println(graph)
        println(sequences)
        part1(graph, sequences).println()
        part2(graph, sequences).println()
    }
}
