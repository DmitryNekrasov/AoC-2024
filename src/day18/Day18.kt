package day18

import assertEquals
import println
import readInput
import java.util.LinkedList
import java.util.Queue

fun List<String>.parse() = map { it.split(",").map(String::toInt).let { (x, y) -> x to y } }

fun part1(n: Int, limit: Int, points: List<Pair<Int, Int>>): Int {
    val grid = Array(n) { CharArray(n) { '.' } }
    for (i in 0..<limit) {
        val (x, y) = points[i]
        grid[y][x] = '#'
    }

    val visited = Array(n) { BooleanArray(n) }
    val queue: Queue<List<Int>> = LinkedList()
    queue.offer(listOf(0, 0, 0))
    visited[0][0] = true
    while (queue.isNotEmpty()) {
        val (x, y, distance) = queue.poll()
        if (x == n - 1 && y == n - 1) return distance
        for ((dx, dy) in listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)) {
            val nextX = x + dx
            val nextY = y + dy
            if (nextX in 0..<n && nextY in 0..<n && grid[nextY][nextX] == '.' && !visited[nextY][nextX]) {
                visited[nextY][nextX] = true
                queue.offer(listOf(nextX, nextY, distance + 1))
            }
        }
    }

    throw RuntimeException("Should not reach here")
}

fun part2(points: List<Pair<Int, Int>>): Int {
    return points.size
}

fun main() {
    run {
        val n = 7
        val limit = 12
        val points = readInput("Day18_test01").parse()

        run {
            val expected = 22
            val actual = part1(n, limit, points)
            assertEquals(expected, actual)
        }

        run {
            val expected = -1
            val actual = part2(points)
            assertEquals(expected, actual)
        }
    }

    run {
        val n = 71
        val limit = 1024
        val points = readInput("Day18").parse()

        part1(n, limit, points).println()
        part2(points).println()
    }
}
