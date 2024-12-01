import kotlin.math.abs

fun part1(input: List<String>): Int {
    val (list1, list2) = input.map { it.split(" ").filter { it.isNotEmpty() }.map(String::toInt) }
        .fold(mutableListOf<Int>() to mutableListOf<Int>()) { acc, pair ->
            acc.also { (list1, list2) -> list1 += pair[0]; list2 += pair[1] }
        }
    return (list1.sorted() zip list2.sorted()).sumOf { (v1, v2) -> abs(v1 - v2) }
}

fun part2(input: List<String>): Int {
    return input.size * 2
}

fun main() {
    /**
     * Input: Day01_test01
     */
    run {
        val input = readInput("Day01_test01")

        /**
         * Part 1
         * Output: 11
         */
        run {
            val expected = 11
            val actual = part1(input)
            assertEquals(expected, actual)
        }

        /**
         * Part 2
         * Output: 2
         */
        run {
            val expected = 2
            val actual = part2(input)
            assertEquals(expected, actual)
        }
    }

    /**
     * Input: Day01
     */
    run {
        val input = readInput("Day01")
        part1(input).println()
        part2(input).println()
    }
}
