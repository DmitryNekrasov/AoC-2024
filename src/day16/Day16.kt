package day16

import assertEquals
import println
import readInput

const val N = 0
const val E = 1
const val S = 2
const val W = 3

fun part1(grid: List<String>): Int {
    val n = grid.size
    val m = grid.first().length
    val visited = Array(n) { BooleanArray(m) }
    val graph = HashMap<Int, MutableSet<Pair<Int, Int>>>()

    fun id(i: Int, j: Int, direction: Int) = (i * m + j) * 4 + direction

    fun dfs(i: Int, j: Int) {
        visited[i][j] = true

        for (k in N..W) {
            graph.getOrPut(id(i, j, k)) { mutableSetOf() } += id(i, j, (k + 1) % 4) to 1000
            graph.getOrPut(id(i, j, (k + 1) % 4)) { mutableSetOf() } += id(i, j, k) to 1000
        }

        when {
            grid[i - 1][j] != '#' -> {
                graph.getOrPut(id(i, j, N)) { mutableSetOf() } += id(i - 1, j, N) to 1
                graph.getOrPut(id(i - 1, j, S)) { mutableSetOf() } += id(i, j, S) to 1
                if (!visited[i - 1][j]) {
                    dfs(i - 1, j)
                }
            }
            grid[i + 1][j] != '#' -> {
                graph.getOrPut(id(i, j, S)) { mutableSetOf() } += id(i + 1, j, S) to 1
                graph.getOrPut(id(i + 1, j, N)) { mutableSetOf() } += id(i, j, N) to 1
                if (!visited[i + 1][j]) {
                    dfs(i + 1, j)
                }
            }
            grid[i][j - 1] != '#' -> {
                graph.getOrPut(id(i, j, W)) { mutableSetOf() } += id(i, j - 1, W) to 1
                graph.getOrPut(id(i, j - 1, E)) { mutableSetOf() } += id(i, j, E) to 1
                if (!visited[i][j - 1]) {
                    dfs(i, j - 1)
                }
            }
            grid[i][j + 1] != '#' -> {
                graph.getOrPut(id(i, j, E)) { mutableSetOf() } += id(i, j + 1, E) to 1
                graph.getOrPut(id(i, j + 1, W)) { mutableSetOf() } += id(i, j, W) to 1
                if (!visited[i][j + 1]) {
                    dfs(i, j + 1)
                }
            }
        }
    }

    return grid.size
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
            val expected = -1
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
            val expected = -1
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
