package day20

import assertEquals
import println
import readInput
import java.util.LinkedList
import java.util.Queue
import kotlin.math.abs

fun List<CharArray>.get(c: Char): Pair<Int, Int> {
    for (i in indices) {
        for (j in first().indices) {
            if (this[i][j] == c) {
                return i to j
            }
        }
    }
    throw RuntimeException("Should not reach here")
}

const val INF = 1_000_000_000

fun List<CharArray>.distances(start: Pair<Int, Int>, end: Pair<Int, Int>): Array<IntArray> {
    val n = size
    val m = first().size
    val distances = Array(n) { IntArray(m) { -INF } }
    val queue: Queue<List<Int>> = LinkedList()
    val (startI, startJ) = start
    queue.offer(listOf(startI, startJ, 0))
    distances[startI][startJ] = 0
    while (queue.isNotEmpty()) {
        val (i, j, distance) = queue.poll()
        for ((di, dj) in listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)) {
            val nextI = i + di
            val nextJ = j + dj
            if (nextI in 0..<n && nextJ in 0..<m && this[nextI][nextJ] != '#' && distances[nextI][nextJ] == -INF) {
                distances[nextI][nextJ] = distance + 1
                queue.offer(listOf(nextI, nextJ, distance + 1))
            }
        }
    }
    return distances
}

fun solve(grid: List<CharArray>, limit: Int, maxSteps: Int): Int {
    val start = grid.get('S')
    val end = grid.get('E')
    val distances = grid.distances(start, end)

    val n = grid.size
    val m = grid.first().size
    val visited = Array(n) { BooleanArray(m) }

    fun Pair<Int, Int>.next(): Pair<Int, Int> {
        for ((di, dj) in listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)) {
            val nextI = first + di
            val nextJ = second + dj
            if (grid[nextI][nextJ] != '#' && !visited[nextI][nextJ]) {
                visited[first][second] = true
                return nextI to nextJ
            }
        }
        throw RuntimeException("Should not reach here")
    }

    var result = 0
    var position = start
    do {
        val (i, j) = position
        for (steps in 2..maxSteps) {
            for (k in -steps..steps) {
                val left = steps - abs(k)
                val right = -left
                for (l in if (left == right) listOf(left) else listOf(left, right)) {
                    if (i + k in 0..<n && j + l in 0..<m) {
                        val save = distances[i + k][j + l] - distances[i][j] - steps
                        if (save >= limit) {
                            result++
                        }
                    }
                }
            }
        }
        position = position.next()
    } while (position != end)

    return result
}

fun part2(grid: List<CharArray>): Int {
    return grid.size
}

fun main() {
    run {
        val input = readInput("Day20_test01").map { it.toCharArray() }

        run {
            val expected = 8
            val actual = solve(input, 12, 2)
            assertEquals(expected, actual)
        }

        run {
            val expected = 285
            val actual = solve(input, 50, 20)
            assertEquals(expected, actual)
        }
    }

    run {
        val input = readInput("Day20").map { it.toCharArray() }
        solve(input, 100, 2).println()
        solve(input, 100, 20).println()
    }
}
