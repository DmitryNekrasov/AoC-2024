package day06

import assertEquals
import println
import readInput

val List<String>.start: Pair<Int, Int>
    get() = run {
        for (i in 0..lastIndex) {
            for (j in 0..this.first().lastIndex) {
                if (this[i][j] == '^') {
                    return@run i to j
                }
            }
        }
        throw RuntimeException("Should not reach here")
    }

val d = listOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1)

const val VISITED = '&'

fun List<CharArray>.traverse(startI: Int, startJ: Int): Boolean {
    val n = size
    val m = first().size
    var i = startI
    var j = startJ
    var currentDirection = 0
    val visited = Array(n) { IntArray(m) }
    do {
        if ((visited[i][j] and (1 shl currentDirection)) != 0) break
        visited[i][j] = visited[i][j] or (1 shl currentDirection)
        this[i][j] = VISITED
        val (di, dj) = d[currentDirection]
        if (i + di !in 0..<n || j + dj !in 0..<m) return false
        if (this[i + di][j + dj] == '#') {
            currentDirection = (currentDirection + 1) % d.size
        } else {
            i += di
            j += dj
        }
    } while (true)
    return true
}

fun List<CharArray>.print() {
    println(joinToString("\n") { String(it) })
}

fun part1(map: List<String>): Int {
    val (startI, startJ) = map.start
    val a = map.map(String::toCharArray)
    a.traverse(startI, startJ)
    return a.sumOf { it.count{ it == VISITED } }
}

fun part2(map: List<String>): Int {
    val (startI, startJ) = map.start
    val a = map.map(String::toCharArray)
    a.traverse(startI, startJ)

    var result = 0
    for (i in 0..a.lastIndex) {
        for (j in 0..a.first().lastIndex) {
            if (a[i][j] == VISITED) {
                a[i][j] = '#'
                if (a.traverse(startI, startJ)) {
                    result++;
                }
                a[i][j] = '.'
            }
        }
    }

    return result
}

fun main() {
    run {
        val input = readInput("Day06_test01")

        run {
            val expected = 41
            val actual = part1(input)
            assertEquals(expected, actual)
        }

        run {
            val expected = 6
            val actual = part2(input)
            assertEquals(expected, actual)
        }
    }

    run {
        val input = readInput("Day06")
        part1(input).println()
        part2(input).println()
    }
}
