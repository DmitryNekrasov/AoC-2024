package day01

import assertEquals
import println
import readInput
import kotlin.math.abs

fun List<String>.splitIntoTwoLists() =
    this.map { it.split(" ").filter { it.isNotEmpty() }.map(String::toInt) }
        .fold(mutableListOf<Int>() to mutableListOf<Int>()) { acc, pair ->
            acc.also { (list1, list2) -> list1 += pair[0]; list2 += pair[1] }
        }

fun part1(list1: List<Int>, list2: List<Int>) =
    (list1.sorted() zip list2.sorted()).sumOf { (v1, v2) -> abs(v1 - v2) }

fun part2(list1: List<Int>, list2: List<Int>): Int {
    val counts = HashMap<Int, Int>()
    for (v in list2) {
        counts[v] = (counts[v] ?: 0) + 1
    }
    return list1.sumOf { it * (counts[it] ?: 0) }
}

fun main() {
    /**
     * Input: Day01_test01
     */
    run {
        val (list1, list2) = readInput("Day01_test01").splitIntoTwoLists()

        /**
         * Part 1
         * Output: 11
         */
        run {
            val expected = 11
            val actual = part1(list1, list2)
            assertEquals(expected, actual)
        }

        /**
         * Part 2
         * Output: 31
         */
        run {
            val expected = 31
            val actual = part2(list1, list2)
            assertEquals(expected, actual)
        }
    }

    /**
     * Input: Day01
     */
    run {
        val (list1, list2) = readInput("Day01").splitIntoTwoLists()
        part1(list1, list2).println()
        part2(list1, list2).println()
    }
}
