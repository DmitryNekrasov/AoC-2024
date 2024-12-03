package day03

import assertEquals
import println
import readInput

const val D = 'q'

fun solve(input: List<String>, isPartOne: Boolean = true): Int {
    val automata = hashMapOf(
        0 to hashMapOf('m' to 1, 'd' to 9),
        1 to hashMapOf('u' to 2),
        2 to hashMapOf('l' to 3),
        3 to hashMapOf('(' to 4),
        4 to hashMapOf(D to 5),
        5 to hashMapOf(D to 5, ',' to 6),
        6 to hashMapOf(D to 7),
        7 to hashMapOf(D to 7, ')' to 8),
        9 to hashMapOf('o' to 10),
        10 to hashMapOf('(' to 11, 'n' to 13),
        11 to hashMapOf(')' to 12),
        13 to hashMapOf('\'' to 14),
        14 to hashMapOf('t' to 15),
        15 to hashMapOf('(' to 16),
        16 to hashMapOf(')' to 17)
    )

    var result = 0
    var doit = true

    for (str in input) {
        var state = 0
        var index = 0
        val number1 = StringBuilder()
        val number2 = StringBuilder()
        while (index < str.length) {
            val c = str[index].let{ if (it.isDigit()) D else it }
            state = if (c in automata[state]!!) automata[state]!![c]!! else 0
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
                    if (isPartOne || doit) {
                        result += number1.toString().toInt() * number2.toString().toInt()
                    }
                    number1.clear()
                    number2.clear()
                    state = 0
                }
                12 -> {
                    doit = true
                    state = 0
                }
                17 -> {
                    doit = false
                    state = 0
                }
            }
            index++
        }
    }

    return result
}

fun part1(input: List<String>) = solve(input)

fun part2(input: List<String>) = solve(input, false)

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
