package day05

import assertEquals
import println
import readInput

fun List<String>.parse(): Pair<Set<Pair<Int, Int>>, List<List<Int>>> {
    fun List<String>.parseRules() =
        this.map { it.split("|").map(String::toInt) }.map { (from, to) -> from to to }.toSet()

    fun List<String>.parseSequences() = this.map { it.split(",").map(String::toInt) }

    val (pageOrdering, updateString) = this.indexOf("")
        .let { index -> this.slice(0..<index) to this.slice((index + 1)..this.lastIndex) }

    return pageOrdering.parseRules() to updateString.parseSequences()
}

fun List<List<Int>>.partition(rules: Set<Pair<Int, Int>>) = this.partition { sequence ->
    var isCorrect = true
    for (i in 0..<sequence.lastIndex) {
        val from = sequence[i]
        for (j in (i + 1)..sequence.lastIndex) {
            val to = sequence[j]
            if (from to to !in rules) {
                isCorrect = false
            }
        }
    }
    isCorrect
}

fun part1(sequences: List<List<Int>>): Int {
    return sequences.sumOf { it[it.size / 2] }
}

fun part2(rules: Set<Pair<Int, Int>>, incorrect: List<List<Int>>): Int {
    println("rules = $rules")
    println("incorrect = $incorrect")

    return rules.size
}

fun main() {
    run {
        val (rules, sequences) = readInput("Day05_test01").parse()
        val (correct, incorrect) = sequences.partition(rules)

        run {
            val expected = 143
            val actual = part1(correct)
            assertEquals(expected, actual)
        }

        run {
            val expected = 123
            val actual = part2(rules, incorrect)
            assertEquals(expected, actual)
        }
    }

    run {
        val (rules, sequences) = readInput("Day05").parse()
        val (correct, incorrect) = sequences.partition(rules)

        part1(correct).println()
        part2(rules, incorrect).println()
    }
}
