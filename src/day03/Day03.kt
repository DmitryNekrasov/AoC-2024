package day03

import assertEquals
import println
import readInput

const val D = 'q'

fun part1(input: List<String>): Int {
    val automata = arrayOf(
        hashMapOf('m' to 1),
        hashMapOf('u' to 2),
        hashMapOf('l' to 3),
        hashMapOf('(' to 4),
        hashMapOf(D to 5),
        hashMapOf(D to 5, ',' to 6),
        hashMapOf(D to 7),
        hashMapOf(D to 7, ')' to 8)
    )

    var result = 0

    for (str in input) {
        var state = 0
        var index = 0
        val number1 = StringBuilder()
        val number2 = StringBuilder()
        while (index < str.length) {
            val c = str[index].let{ if (it.isDigit()) D else it }
            state = if (c in automata[state]) automata[state][c]!! else 0
            when (state) {
                0 -> {
                    number1.clear()
                    number2.clear()
                }
                5 -> {
                    number1.append(str[index])
                }
                7 -> {
                    number2.append(str[index])
                }
                8 -> {
                    result += number1.toString().toInt() * number2.toString().toInt()
                    number1.clear()
                    number2.clear()
                    state = 0
                }
            }
            index++
        }
    }

    return result
}

fun part2(input: List<String>): Int {
    return input.size
}

fun main() {
    run {
        val input = readInput("Day03_test01")

        run {
            val expected = 161
            val actual = part1(input)
            assertEquals(expected, actual)
        }

        run {
            val expected = 48
            val actual = part2(input)
            assertEquals(expected, actual)
        }
    }

    run {
        val input = readInput("Day03")
        part1(input).println()
        part2(input).println()
    }
}
