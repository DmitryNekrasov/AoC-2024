package day20

import assertEquals
import println
import readInput
import java.util.LinkedList
import java.util.Queue

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

fun List<CharArray>.distance(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
    val n = size
    val m = first().size
    val visited = Array(n) { BooleanArray(m) }
    val queue: Queue<List<Int>> = LinkedList()
    val (startI, startJ) = start
    queue.offer(listOf(startI, startJ, 0))
    visited[startI][startJ] = true
    while (queue.isNotEmpty()) {
        val (i, j, distance) = queue.poll()
        if (i to j == end) return distance
        for ((di, dj) in listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)) {
            val nextI = i + di
            val nextJ = j + dj
            if (nextI in 0..<n && nextJ in 0..<m && this[nextI][nextJ] != '#' && !visited[nextI][nextJ]) {
                visited[nextI][nextJ] = true
                queue.offer(listOf(nextI, nextJ, distance + 1))
            }
        }
    }
    throw RuntimeException("Should not reach here")
}

fun part1(grid: List<CharArray>, limit: Int): Int {
    val start = grid.get('S')
    val end = grid.get('E')
    val initialDistance = grid.distance(start, end)

    val n = grid.size
    val m = grid.first().size
    val distances = mutableListOf<Int>()
    for (i in 1..<grid.lastIndex) {
        for (j in 1..<grid.first().lastIndex) {
            if (grid[i][j] == '#') {
                grid[i][j] = '.'
                distances += grid.distance(start, end)
                grid[i][j] = '#'
            }
        }
    }

    return distances.count { initialDistance - it >= limit }
}

fun part2(grid: List<CharArray>): Int {
    return grid.size
}

fun main() {
    run {
        val input = readInput("Day20_test01").map { it.toCharArray() }

        run {
            val expected = 8
            val actual = part1(input, 12)
            assertEquals(expected, actual)
        }

        run {
            val expected = -1
            val actual = part2(input)
            assertEquals(expected, actual)
        }
    }

    run {
        val input = readInput("Day20").map { it.toCharArray() }
        part1(input, 100).println()
        part2(input).println()
    }
}
