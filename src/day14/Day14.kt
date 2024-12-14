package day14

import assertEquals
import println
import readInput
import java.io.File

fun String.parse() = this.split(" ").flatMap { it.substring(2).split(",").map(String::toInt) }.toIntArray()

fun List<String>.parse() = this.map(String::parse)

const val TIME = 100

fun part1(input: List<IntArray>, n: Int, m: Int): Int {
    val finalPoints = input.map { (x, y, dx, dy) -> (x + dx * TIME).mod(n) to (y + dy * TIME).mod(m) }
    return finalPoints.count { (x, y) -> x < n / 2 && y < m / 2 } *
            finalPoints.count { (x, y) -> x < n / 2 && y > m / 2 } *
            finalPoints.count { (x, y) -> x > n / 2 && y < m / 2 } *
            finalPoints.count { (x, y) -> x > n / 2 && y > m / 2 }
}

fun CharArray.isSymmetric(): Boolean {
    for (i in 0..size / 2) {
        if (this[i] != this[size - 1 - i]) {
            return false
        }
    }
    return true
}

fun Array<CharArray>.isSymmetric() = this.all(CharArray::isSymmetric)

fun part2(input: List<IntArray>, n: Int, m: Int): Int {
    val map = Array(m) { CharArray(n) { '.' } }

    val outFile = File("out.txt")
    outFile.writeText("")

    var time = 0
    do {
        outFile.appendText("\n---------------- TIME = $time ----------------\n")
        for ((x, y, _, _) in input) {
            map[y][x] = '#'
        }
        val s = map.joinToString("\n") { it.joinToString("") }
        outFile.appendText(s)
        for ((x, y, _, _) in input) {
            map[y][x] = '.'
        }
        for (robot in input) {
            robot[0] = (robot[0] + robot[2]).mod(n)
            robot[1] = (robot[1] + robot[3]).mod(m)
        }
        time++
    } while (time < 15_000)

    return input.size
}

fun main() {
    run {
        val input = readInput("Day14_test01").parse()
        val n = 11
        val m = 7

        run {
            val expected = 12
            val actual = part1(input, n, m)
            assertEquals(expected, actual)
        }
    }

    run {
        val input = readInput("Day14").parse()
        val n = 101
        val m = 103
        part1(input, n, m).println()
        part2(input, n, m)
    }
}
