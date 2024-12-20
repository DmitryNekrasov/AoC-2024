# Day 20: Race Condition

<p align="center">
  <img src="aoc-day-20.png"/>
</p>

## Intuition

The core idea is to find all possible "cheats" - paths that save a certain amount of time by going through walls. The problem can be broken down into two key insights:

1. First, we need to find the baseline shortest path distances from the start to every point without any cheating. This gives us a reference point to calculate how much time we save with cheats.

2. Second, for each point on the valid path from start to end, we can explore all possible "cheat destinations" within the allowed cheat range (2 moves in part 1, 20 moves in part 2) and calculate the time saved.

## Approach

The solution uses several clever techniques:

### 1. Distance Calculation
```kotlin
fun List<CharArray>.distances(start: Pair<Int, Int>): Array<IntArray>
```
- Uses BFS (Breadth-First Search) to calculate shortest paths from the start position to every reachable point
- Creates a distance matrix where each cell contains the minimum number of steps needed to reach that position
- Uses -INF to mark unreachable positions
- This becomes our baseline for calculating time savings

### 2. Cheat Analysis
```kotlin
fun solve(grid: List<CharArray>, limit: Int, maxSteps: Int): Int
```
- Takes three parameters:
    - `grid`: The maze layout
    - `limit`: Minimum time that needs to be saved (100 in both parts)
    - `maxSteps`: Maximum cheat distance (2 for part 1, 20 for part 2)

- For each position on the valid path:
    1. Explores all possible cheat destinations within the allowed range
    2. Uses a diamond-shaped pattern to check all reachable positions within `maxSteps`
    3. Calculates time saved using: `actual_distance - (cheat_distance + steps_used)`

### 3. Path Traversal
```kotlin
fun Pair<Int, Int>.next(): Pair<Int, Int>
```
- Uses a visited array to track the path from start to end
- For each position, finds the next unvisited valid move
- This ensures we only check cheat possibilities from valid path positions

## Complexity

### Time Complexity
1. Distance Calculation (BFS):
    - $$O(N * M)$$ where N and M are the grid dimensions

2. Main Solution:
    - For each position on path ($$O(P)$$ where P is path length (N * M)):
        - For each possible cheat length ($$O(maxSteps)$$)
            - For each possible direction within cheat range ($$O(maxSteps)$$)
    - Total: $$O(N * M * maxSteps^2)$$

### Space Complexity
- Distance Matrix: $$O(N * M)$$
- Visited Array: $$O(N * M)$$
- Queue for BFS: $$O(N * M)$$
- Total: $$O(N * M)$$