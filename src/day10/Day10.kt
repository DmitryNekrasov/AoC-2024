package day10

import assertEquals
import println
import readInput

fun List<String>.getTrailheadScore(startI: Int, startJ: Int): Pair<Int, Int> {
    val n = size
    val m = first().length
    val visited = Array(n) { BooleanArray(m) }
    val finals = HashSet<Pair<Int, Int>>()
    var trailCount = 0

    fun dfs(i: Int, j: Int) {
        if (this[i][j] == '9') {
            finals += i to j
            trailCount++
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

    return finals.size to trailCount
}

fun solve(grid: List<String>): Pair<Int, Int> {
    var totalScore = 0
    var totalRating = 0
    for (i in grid.indices) {
        for (j in grid.first().indices) {
            if (grid[i][j] == '0') {
                val (score, rating) = grid.getTrailheadScore(i, j)
                totalScore += score
                totalRating += rating
            }
        }
    }
    return totalScore to totalRating
}

fun main() {
    run {
        val input = readInput("Day10_test01")
        val (actualScore, actualRating) = solve(input)

        run {
            val expected = 36
            assertEquals(expected, actualScore)
        }

        run {
            val expected = 81
            assertEquals(expected, actualRating)
        }
    }

    run {
        val input = readInput("Day10")
        solve(input).println()
    }
}
