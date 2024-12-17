package day17

import assertEquals
import println
import readInput

fun List<String>.parse() =
    take(3).map { it.split(": ").last().toInt() } to last().split(": ").last().split(",").map(String::toInt)

const val MOD = 8

const val ADV = 0
const val BXL = 1
const val BST = 2
const val JNZ = 3
const val BXC = 4
const val OUT = 5
const val BDV = 6
const val CDV = 7

fun perform(aReg: Int, bReg: Int, cReg: Int, program: List<Int>): List<Int> {
    var aReg = aReg
    var bReg = bReg
    var cReg = cReg

    val output = mutableListOf<Int>()

    var pc = 0
    while (pc < program.size) {
        val opcode = program[pc]
        val literalOperand = program[pc + 1]
        val comboOperand = lazy {
            when (literalOperand) {
                in 0..3 -> literalOperand
                4 -> aReg
                5 -> bReg
                6 -> cReg
                else -> throw RuntimeException("Should not reach here")
            }
        }

        fun dv() = aReg / (1 shl comboOperand.value)

        when (opcode) {
            ADV -> {
                aReg = dv()
                pc += 2
            }

            BXL -> {
                bReg = bReg xor literalOperand
                pc += 2
            }

            BST -> {
                bReg = comboOperand.value % MOD
                pc += 2
            }

            JNZ -> {
                if (aReg != 0) {
                    pc = literalOperand
                } else {
                    pc += 2
                }
            }

            BXC -> {
                bReg = bReg xor cReg
                pc += 2
            }

            OUT -> {
                output += comboOperand.value % MOD
                pc += 2
            }

            BDV -> {
                bReg = dv()
                pc += 2
            }

            CDV -> {
                cReg = dv()
                pc += 2

            }

            else -> throw RuntimeException("Should not reach here")
        }
    }

    return output
}

fun part1(aReg: Int, bReg: Int, cReg: Int, program: List<Int>): String {
    return perform(aReg, bReg, cReg, program).joinToString(",")
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
