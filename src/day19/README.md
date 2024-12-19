# Day 19: Linen Layout

Here's a detailed analysis of the solution:

## Intuition
The problem is essentially about pattern matching and counting combinations. For part 1, we need to determine if a design can be created using available patterns, while part 2 requires counting all possible ways to create each design.

The key insight is that instead of trying to match patterns directly (which could be slow), we can use a rolling hash function to convert patterns into numbers and make comparisons much faster.

## Approach

### 1. Pattern Hashing
```kotlin
const val BASE = 283
const val MOD = 1_000_000_000_000_000 - 11

fun String.hashes(from: Int, to: Int): LongArray {
    val result = LongArray(to - from)
    result[0] = this[from].code.toLong()
    for (i in (from + 1)..<to) {
        result[i - from] = (result[i - from - 1] * BASE % MOD + this[i].code.toLong()) % MOD
    }
    return result
}
```
- Uses a rolling hash function
- Each substring gets a unique hash value
- `BASE` and `MOD` are chosen to minimize collisions
- Computes all possible prefix hashes from a given starting position

### 2. Pattern Organization
```kotlin
val hashDict = patterns.groupBy { it.length }
    .mapValues { (_, fixedLenPatterns) -> fixedLenPatterns.map(String::hash).toSet() }
```
- Groups patterns by length for efficient lookup
- Converts each pattern to its hash value
- Uses a Set for O(1) lookup time

### 3. Part 1: Pattern Matching
```kotlin
fun String.backtrack(from: Int = 0): Boolean {
    if (from == length) return true
    return hashes(from, min(from + maxPatternLen, length)).withIndex()
        .any { (index, hash) -> if (hash in hashDict[index + 1]!!) backtrack(from + index + 1) else false }
}
```
- Uses backtracking to try different pattern combinations
- For each position, tries all possible length patterns that could start there
- Returns true if any valid combination is found

### 4. Part 2: Counting Combinations
```kotlin
fun String.backtrack(from: Int = 0): Long {
    if (from == length) return 1L
    return cache.getOrPut(from) {
        hashes(from, min(from + maxPatternLen, length)).withIndex()
            .sumOf { (index, hash) -> if (hash in hashDict[index + 1]!!) backtrack(from + index + 1) else 0L }
    }
}
```
- Similar to Part 1 but counts all possible combinations
- Uses dynamic programming with memoization to avoid recalculating same subproblems
- Cache is cleared between different designs

## Complexity

### Time Complexity
Let n be the length of a design and m be the maximum pattern length:
- Computing rolling hashes: $$O(n)$$
- Part 1: $$O(n * m)$$ per design in the worst case
- Part 2: $$O(n * m)$$ per design with memoization
- Overall: $$O(D * n * m)$$ where D is the number of designs

### Space Complexity
- Pattern hash dictionary: $$O(P)$$ where P is total length of all patterns
- Rolling hash array: $$O(m)$$
- Part 2 cache: $$O(n)$$ per design
- Overall: $$O(P + m + n)$$

The solution is efficient because:
1. Uses rolling hashes instead of string comparisons
2. Groups patterns by length to reduce search space
3. Uses memoization to avoid redundant calculations in Part 2
4. Maintains reasonable space complexity by clearing cache between designs