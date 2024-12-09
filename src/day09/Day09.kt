package day09

import assertEquals
import println
import readInput

fun Int.repeat(n: Int) = List(n) { this }

fun part1(input: String): Long {
    val blocks = input.withIndex().fold(mutableListOf<Int>()) { acc, p ->
        acc.also {
            acc += (if ((p.index and 1) == 0) p.index / 2 else -1).repeat(p.value.code - '0'.code)
        }
    }

    var p1 = 0
    var p2 = blocks.lastIndex
    while (p1 < p2) {
        if (blocks[p1] != -1) {
            p1++
            continue
        }
        if (blocks[p2] == -1) {
            p2--
            continue
        }
        blocks[p1] = blocks[p2]
        blocks[p2] = -1
        p1++
        p2--
    }

    return blocks.filter { it != -1 }.withIndex().sumOf { (index, value) -> index * value.toLong() }
}

fun part2(input: String): Long {
    val blocks = input.map { c -> mutableListOf(c.code - '0'.code) }

    val newIndices = Array(blocks.size) { mutableListOf<Int>() }
    for (i in newIndices.indices) {
        if ((i and 1) == 0) {
            newIndices[i] += i / 2
        }
    }
    var ptr = blocks.lastIndex
    while (ptr > 0) {
//        println(blocks)
        var candidate = 1
        while (candidate < ptr) {
            if (blocks[candidate].last() >= blocks[ptr].last()) break
            candidate += 2
        }
        if (candidate < ptr) {
            val currentEmpty = blocks[candidate].removeAt(blocks[candidate].lastIndex)
            blocks[candidate] += listOf(0, blocks[ptr].last(), currentEmpty - blocks[ptr].last())
            val currentFill = blocks[ptr].removeAt(blocks[ptr].lastIndex)
            blocks[ptr] += listOf(0, currentFill, 0)
            newIndices[candidate] += ptr / 2
            newIndices[ptr].removeAt(newIndices[ptr].lastIndex)
        }
        ptr -= 2
    }

//    00...111...2...333.44.5555.6666.777.888899
//    0099.111...2...333.44.5555.6666.777.8888..
//    0099.1117772...333.44.5555.6666.....8888..
//    0099.111777244.333....5555.6666.....8888..
//    00992111777.44.333....5555.6666.....8888..

    // 20201030312134414542

    // [[2], [0, 2, 0, 1, 0], [3], [0, 3, 0], [0, 1, 0], [0, 2, 1], [3], [1], [0, 2, 0], [1], [4], [1], [4], [1], [0, 3, 0], [1], [4], [0], [0, 2, 0]]
    // [2] 0 [2] 0 [1] 0 [3] 0 [3] 0 [0] 1 [0] 0 [2] 1 [3] 1 [0] 2 [0] 1 [4] 1 [4] 1 [0] 3 [0] 1 [4] 0 [0] 2 [0]
    //  0     9     2     1     7        .        4  .  3  .     .     .  5  .  6  .     .     .  8        .

    val blocksFlatten = blocks.flatten()
    val newIndicesFlatten = newIndices.toList().flatten()
    var p1 = 0
    var p2 = 0
    val finalBlocks = mutableListOf<Int>()
    while (p2 < newIndicesFlatten.size) {
        if ((p1 and 1) == 0) {
            if (blocksFlatten[p1] != 0) {
                finalBlocks += newIndicesFlatten[p2].repeat(blocksFlatten[p1])
                p2++
            }
        } else {
            finalBlocks += 0.repeat(blocksFlatten[p1])
        }
        p1++
    }

//    println(blocksFlatten)
//    println(newIndicesFlatten)
//    println(finalBlocks)

    return finalBlocks.withIndex().sumOf { (index, value) -> index * value.toLong() }
}

fun main() {
    run {
        val input = readInput("Day09_test01").first()

        run {
            val expected = 1928L
            val actual = part1(input)
            assertEquals(expected, actual)
        }

        run {
            val expected = 2858L
            val actual = part2(input)
            assertEquals(expected, actual)
        }
    }

    run {
        val input = readInput("Day09").first()
        part1(input).println()
        part2(input).println()
    }
}
