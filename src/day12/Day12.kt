package day12

import assertEquals
import println
import readInput

fun part1(grid: List<String>): Int {
    val n = grid.size
    val m = grid.first().length
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

fun part2(grid: List<String>): Int {
    val n = grid.size
    val m = grid.first().length
    val visited = Array(n) { BooleanArray(m) }

    val verticalTransitions = HashMap<Pair<Int, Int>, MutableList<Int>>()
    val horizontalTransitions = HashMap<Pair<Int, Int>, MutableList<Int>>()

    fun dfs(i: Int, j: Int, c: Char, prevI: Int = -1, prevJ: Int = -1): Int {
        if (i !in 0..<n || j !in 0..<m || grid[i][j] != c) {
            if (prevI == i) {
                verticalTransitions.getOrPut(prevJ to j) { mutableListOf() } += i
            } else {
                horizontalTransitions.getOrPut(prevI to i) { mutableListOf() } += j
            }
            return 0
        }
        if (visited[i][j]) return 0
        visited[i][j] = true
        var area = 1
        for ((di, dj) in listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)) {
            area += dfs(i + di, j + dj, c, i, j)
        }
        return area
    }

    fun List<Int>.numberOfContinuousSections(): Int {
        var result = 0
        for (i in 1..lastIndex) {
            if (this[i] - this[i - 1]  > 1) {
                result++
            }
        }
        return result + 1
    }

    var result = 0
    for (i in 0..<n) {
        for (j in 0..<m) {
            if (!visited[i][j]) {
                verticalTransitions.clear()
                horizontalTransitions.clear()
                val area = dfs(i, j, grid[i][j])
                val edgeNumber = verticalTransitions.values.sumOf { it.sorted().numberOfContinuousSections() } +
                        horizontalTransitions.values.sumOf { it.sorted().numberOfContinuousSections() }
                result += area * edgeNumber
            }
        }
    }

    return result
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
            val expected = 1206
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
