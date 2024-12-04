# Day 4: Ceres Search

## Problem Summary
An interesting twist on the classic word search puzzle with two parts:
1. Part 1: Find all occurrences of "XMAS" in any direction (horizontal, vertical, diagonal, even backwards)
2. Part 2: Find X-shaped patterns where each diagonal contains "MAS" (can be forwards or backwards)

## Part 1: Finding XMAS

### Intuition
The key insight is to systematically check every possible starting position and direction for the word "XMAS". Since words can go in any direction (including diagonals and backwards), we need to consider all 8 possible directions from each starting point.

### Approach
1. For each cell (i,j) in the grid:
    - Consider it as a potential starting point
    - Check all 8 directions using direction vectors (di,dj)
    - For each direction, try to match all characters of "XMAS"

The solution uses these key components:
```kotlin
// Generate all 8 directions (excluding 0,0)
fun generateDirections(): List<Pair<Int, Int>> {
    val directions = mutableListOf<Pair<Int, Int>>()
    for (di in -1..1) {
        for (dj in -1..1) {
            if (di != 0 || dj != 0) {
                directions.add(di to dj)
            }
        }
    }
    return directions
}
```

The accumulator array keeps track of how many matching characters we've found in each direction, making it easy to count complete matches when accumulator[i] equals the length of "XMAS".

## Part 2: Finding X-MAS Patterns

### Intuition
Instead of looking for a single word, we're now looking for an X-shaped pattern where:
- The center must be 'A'
- The diagonals must each form "MAS" (either direction)
- The pattern effectively forms an X where each diagonal is a valid "MAS"

### Approach
1. Only consider cells containing 'A' as potential centers
2. For each 'A':
    - Check the four diagonal positions (-1,-1), (-1,1), (1,-1), (1,1)
    - Collect these characters and check if they form a valid pattern

The solution efficiently uses a pattern string "MMSSMMS" which contains all valid combinations of the diagonal characters, allowing for a simple string containment check.

## Time Complexity
- Part 1: $$O(n * m * d)$$ where n,m are grid dimensions and d is number of directions (8)
- Part 2: $$O(n * m)$$ as we only check cells containing 'A' and their immediate diagonals

## Space Complexity
- Part 1: $$O(1)$$ - only uses fixed-size arrays
- Part 2: $$O(1)$$ - only uses a small StringBuilder for pattern matching