# Day 8: Resonant Collinearity

## Intuition
This is a puzzle where we need to find points where antenna signals create antinodes:

### Part 1: Strict Distance Rule
- Two antennas of the same frequency (same character) create antinodes
- The key rule is that one antenna must be twice as far from the antinode as the other
- For any two antennas, this creates two possible antinode points:
    1. One point before the first antenna
    2. One point after the second antenna

### Part 2: Collinearity Rule
- The rules are relaxed - any point that's in line with two antennas of the same frequency becomes an antinode
- This means all points along the line between (and beyond) two same-frequency antennas are antinodes
- The antennas themselves can be antinodes if they're in line with other antennas

## Approach

### Common Structure
1. The solution uses a shared `solve` function that:
   ```kotlin
   fun solve(input: List<String>, antinode: Pair<Int, Int>.(Pair<Int, Int>) -> List<Pair<Int, Int>>)
   ```
    - Takes the input grid and an antinode calculation function
    - Returns count of unique antinode positions

2. Key Components:
    - `getCoordinates()`: Groups antenna positions by their frequency
    - `calculateAntinodes()`: For each pair of same-frequency antennas, calculates antinodes
    - Uses HashSet to track unique antinode positions

### Part 1 Implementation
```kotlin
fun Pair<Int, Int>.antinode(other: Pair<Int, Int>): List<Pair<Int, Int>> {
    val diff = other - this
    return listOf(this - diff, other + diff).filter { it.inRange(input) }
}
```
- For each antenna pair, calculates two potential antinode positions
- Uses vector arithmetic to find points at double distance
- Filters out points outside grid boundaries

### Part 2 Implementation
```kotlin
fun Pair<Int, Int>.antinode(other: Pair<Int, Int>): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    val diff = other - this
    var current = this
    while (current.inRange(input)) {
        result += current
        current -= diff
    }
    current = other
    while (current.inRange(input)) {
        result += current
        current += diff
    }
    return result
}
```
- For each antenna pair, finds all points along their line
- Extends in both directions until hitting grid boundaries
- Includes all points, including antenna positions themselves

## Complexity

### Space Complexity
- $$O(N * M)$$ where `N * M` is the grid size
    - Coordinates map: $$O(A)$$ where A is number of antennas
    - Antinodes HashSet: $$O(N * M)$$ worst case

### Time Complexity
For both parts:
1. Grid Parsing: $$O(N * M)$$
2. Per frequency processing:
    - For each frequency with k antennas:
        - $$O(k^2)$$ antenna pairs
        - Part 1: $$O(1)$$ per pair for antinode calculation
        - Part 2: $$O(max(N, M))$$ per pair for line extension

Overall:
- Part 1: $$O(N * M + F * K^2)$$ where F is number of frequencies, K is max antennas per frequency
- Part 2: $$O(N * M + F * K^2 * max(N, M))$$