package day12

import assertEquals
import println
import readInput

fun part1(input: List<String>): Int {
    val grid = input.map { it.toCharArray() }
    val n = grid.size
    val m = grid.first().size
    val visited = Array(n) { BooleanArray(m) }

    fun dfs(i: Int, j: Int, c: Char): Pair<Int, Int> {  // area -> perimeter
        if (i !in 0..<n || j !in 0..<m || grid[i][j] != c) return 0 to 1
        if (visited[i][j]) return 0 to 0
        visited[i][j] = true
        var totalArea = 1
        var totalPerimeter = 0
        for ((di, dj) in listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)) {
            val (area, perimeter) = dfs(i + di, j + dj, c)
            totalArea += area
            totalPerimeter += perimeter
        }
        return totalArea to totalPerimeter
    }

    var result = 0
    for (i in 0..<n) {
        for (j in 0..<m) {
            if (!visited[i][j]) {
                val (area, perimeter) = dfs(i, j, grid[i][j])
                result += area * perimeter
            }
        }
    }

    return result
}

fun part2(input: List<String>): Int {
    return input.size
}

fun main() {
    run {
        val input = readInput("Day12_test01")

        run {
            val expected = 1930
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
        val input = readInput("Day12")
        part1(input).println()
        part2(input).println()
    }
}
