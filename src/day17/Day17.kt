package day17

import assertEquals
import println
import readInput

fun List<String>.parse() =
    take(3).map { it.split(": ").last().toInt() } to last().split(": ").last().split(",").map(String::toInt)

fun part1(aReg: Int, bReg: Int, cReg: Int, program: List<Int>): String {
    println("A = $aReg")
    println("B = $bReg")
    println("C = $cReg")
    println("program: $program")

    return ""
}

fun part2(aReg: Int, bReg: Int, cReg: Int, program: List<Int>): String {
    return ""
}

fun main() {
    run {
        val (registers, program) = readInput("Day17_test01").parse()
        val (aReg, bReg, cReg) = registers

        run {
            val expected = "4,6,3,5,6,3,5,2,1,0"
            val actual = part1(aReg, bReg, cReg, program)
            assertEquals(expected, actual)
        }

        run {
            val expected = "^_^"
            val actual = part2(aReg, bReg, cReg, program)
            assertEquals(expected, actual)
        }
    }

    run {
        val (registers, program) = readInput("Day17").parse()
        val (aReg, bReg, cReg) = registers
        part1(aReg, bReg, cReg, program).println()
        part2(aReg, bReg, cReg, program).println()
    }
}
