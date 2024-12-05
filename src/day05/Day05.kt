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

fun part1(rules: Set<Pair<Int, Int>>, sequences: List<List<Int>>): Int {
    var result = 0
    for (sequence in sequences) {
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
        if (isCorrect) {
            result += sequence[sequence.size / 2]
        }
    }
    return result
}

fun part2(rules: Set<Pair<Int, Int>>, sequences: List<List<Int>>): Int {
    return rules.size
}

fun main() {
    run {
        val (rules, sequences) = readInput("Day05_test01").parse()
        println("rules = $rules")
        println("sequences = $sequences")

        run {
            val expected = 143
            val actual = part1(rules, sequences)
            assertEquals(expected, actual)
        }

        run {
            val expected = -1
            val actual = part2(rules, sequences)
            assertEquals(expected, actual)
        }
    }

    run {
        val (rules, sequences) = readInput("Day05").parse()
        println("rules = $rules")
        println("sequences = $sequences")

        part1(rules, sequences).println()
        part2(rules, sequences).println()
    }
}
