package day16

import assertEquals
import println
import readInput
import java.util.LinkedList
import java.util.PriorityQueue
import java.util.Queue

const val N = 0
const val E = 1
const val S = 2
const val W = 3

fun List<String>.get(c: Char): Pair<Int, Int> {
    for (i in indices) {
        for (j in first().indices) {
            if (this[i][j] == c) {
                return i to j
            }
        }
    }
    throw RuntimeException("Should not reach here")
}

fun List<String>.id(i: Int, j: Int, direction: Int) = (i * first().length + j) * 4 + direction

fun List<String>.generateGraph(startI: Int, startJ: Int): HashMap<Int, MutableSet<Pair<Int, Int>>> {
    val graph = HashMap<Int, MutableSet<Pair<Int, Int>>>()

    val queue: Queue<Pair<Int, Int>> = LinkedList()
    queue.offer(startI to startJ)

    val visited = Array(size) { BooleanArray(first().length) }

    while (queue.isNotEmpty()) {
        val (i, j) = queue.poll()
        visited[i][j] = true

        for (k in N..W) {
            graph.getOrPut(id(i, j, k)) { mutableSetOf() } += id(i, j, (k + 1) % 4) to 1000
            graph.getOrPut(id(i, j, (k + 1) % 4)) { mutableSetOf() } += id(i, j, k) to 1000
        }

        if (this[i - 1][j] != '#') {
            graph.getOrPut(id(i, j, N)) { mutableSetOf() } += id(i - 1, j, N) to 1
            graph.getOrPut(id(i - 1, j, S)) { mutableSetOf() } += id(i, j, S) to 1
            if (!visited[i - 1][j]) {
                queue.offer(i - 1 to j)
            }
        }
        if (this[i + 1][j] != '#') {
            graph.getOrPut(id(i, j, S)) { mutableSetOf() } += id(i + 1, j, S) to 1
            graph.getOrPut(id(i + 1, j, N)) { mutableSetOf() } += id(i, j, N) to 1
            if (!visited[i + 1][j]) {
                queue.offer(i + 1 to j)
            }
        }
        if (this[i][j - 1] != '#') {
            graph.getOrPut(id(i, j, W)) { mutableSetOf() } += id(i, j - 1, W) to 1
            graph.getOrPut(id(i, j - 1, E)) { mutableSetOf() } += id(i, j, E) to 1
            if (!visited[i][j - 1]) {
                queue.offer(i to j - 1)
            }
        }
        if (this[i][j + 1] != '#') {
            graph.getOrPut(id(i, j, E)) { mutableSetOf() } += id(i, j + 1, E) to 1
            graph.getOrPut(id(i, j + 1, W)) { mutableSetOf() } += id(i, j, W) to 1
            if (!visited[i][j + 1]) {
                queue.offer(i to j + 1)
            }
        }
    }

    return graph
}

const val INF = 1_000_000_000

fun part1(grid: List<String>): Int {
    val (startI, startJ) = grid.get('S')
    val (endI, endJ) = grid.get('E')
    val graph = grid.generateGraph(startI, startJ)

    val n = grid.size
    val m = grid.first().length
    val vertexNumber = 4 * m * (n + 1) + 5
    for (k in N..W) {
        graph.getOrPut(grid.id(endI, endJ, k)) { mutableSetOf() } += vertexNumber - 1 to 0
    }

    val d = IntArray(vertexNumber) { INF }
    val p = Array(vertexNumber) { mutableListOf<Int>() }
    val startId = grid.id(startI, startJ, E)
    d[startId] = 0
    val queue = PriorityQueue<Int> { i, j -> d[i] compareTo d[j] }
    queue.offer(startId)
    while (queue.isNotEmpty()) {
        val from = queue.poll()
        graph[from]?.also { neighbors ->
            for ((to, cost) in neighbors) {
                if (d[from] + cost < d[to]) {
                    d[to] = d[from] + cost
                    p[to].clear()
                    p[to] += from
                    queue.offer(to)
                } else if (d[from] + cost == d[to]) {
                    p[to] += from
                }
            }
        }
    }

    return d[vertexNumber - 1]
}

fun part2(input: List<String>): Int {
    return input.size
}

fun main() {
    run {
        val input = readInput("Day16_test01")

        run {
            val expected = 7036
            val actual = part1(input)
            assertEquals(expected, actual)
        }

        run {
            val expected = 45
            val actual = part2(input)
            assertEquals(expected, actual)
        }
    }

    run {
        val input = readInput("Day16_test02")

        run {
            val expected = 11048
            val actual = part1(input)
            assertEquals(expected, actual)
        }

        run {
            val expected = 64
            val actual = part2(input)
            assertEquals(expected, actual)
        }
    }

    run {
        val input = readInput("Day16")
        part1(input).println()
        part2(input).println()
    }
}
