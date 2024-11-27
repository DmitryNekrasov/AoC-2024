fun part1(input: List<String>): Int {
    return input.size
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
         * Output: 1
         */
        run {
            val expected = 1
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
