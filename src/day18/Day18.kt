package day18

import assertEquals
import println
import readInput
import java.util.LinkedList
import java.util.Queue

fun List<String>.parse() = map { it.split(",").map(String::toInt).let { (x, y) -> x to y } }

fun distance(n: Int, limit: Int, points: List<Pair<Int, Int>>): Int {
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

    return -1
}

fun part1(n: Int, limit: Int, points: List<Pair<Int, Int>>): Int {
    return distance(n, limit, points)
}

fun part2(n: Int, points: List<Pair<Int, Int>>): String {
    fun binSearch(left: Int = 0, right: Int = points.size): Int {
        if (left >= right) return right
        val mid = left + (right - left) / 2
        return if (distance(n, mid, points) == -1) binSearch(left, mid) else binSearch(mid + 1, right)
    }

    return points[binSearch() - 1].let { (x, y) -> "$x,$y" }
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
            val expected = "6,1"
            val actual = part2(n, points)
            assertEquals(expected, actual)
        }
    }

    run {
        val n = 71
        val limit = 1024
        val points = readInput("Day18").parse()

        part1(n, limit, points).println()
        part2(n, points).println()
    }
}
