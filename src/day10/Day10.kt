package day10

import assertEquals
import println
import readInput

fun List<String>.getTrailheadScore(startI: Int, startJ: Int): Int {
    val n = size
    val m = first().length
    val visited = Array(n) { BooleanArray(m) }
    val finals = HashSet<Pair<Int, Int>>()

    fun dfs(i: Int, j: Int) {
        if (this[i][j] == '9') {
            finals += i to j
        } else {
            visited[i][j] = true
            for ((di, dj) in listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)) {
                val nextI = i + di
                val nextJ = j + dj
                if (nextI in 0..<n && nextJ in 0..<m && this[nextI][nextJ] - this[i][j] == 1 && !visited[nextI][nextJ]) {
                    dfs(nextI, nextJ)
                }
            }
            visited[i][j] = false
        }
    }

    dfs(startI, startJ)

    return finals.size
}

fun part1(grid: List<String>): Int {
    var result = 0
    for (i in grid.indices) {
        for (j in grid.first().indices) {
            if (grid[i][j] == '0') {
                result += grid.getTrailheadScore(i, j)
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
        val input = readInput("Day10_test01")

        run {
            val expected = 36
            val actual = part1(input)
            assertEquals(expected, actual)
        }

        run {
            val expected = 81
            val actual = part2(input)
            assertEquals(expected, actual)
        }
    }

    run {
        val input = readInput("Day10")
        part1(input).println()
        part2(input).println()
    }
}
