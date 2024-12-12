# Day 12: Garden Groups

<p align="center">
  <img src="aoc-day-12.png"/>
</p>

## Deep Dive into the Problem Structure

### Grid Representation
The garden is represented as a 2D grid where each character represents a plant type. Let's look at this small example:
```
AAAA
BBCD
BBCC
EEEC
```
This structure is crucial because it presents several key challenges:
1. Connected regions may have irregular shapes
2. A single plant type can form multiple disconnected regions
3. Regions can contain "holes" filled with different plant types

### Region Properties
A region has several important characteristics:
1. Area: The count of connected cells of the same plant type
2. Perimeter (Part 1): The count of edges that either:
    - Face the grid boundary
    - Face a different plant type
3. Distinct Edges (Part 2): The count of straight-line segments forming the region's boundary

## Detailed Solution Breakdown

### Part 1: Area and Perimeter Calculation

Let's analyze the DFS implementation in detail:

```kotlin
fun dfs(i: Int, j: Int, c: Char): Pair<Int, Int> {
    if (i !in 0..<n || j !in 0..<m || grid[i][j] != c) return 0 to 1
    if (visited[i][j]) return 0 to 0
    visited[i][j] = true
    var totalArea = 1
    var totalPerimeter = 0
    for ((di, dj) in listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)) {
        val (area, perimeter) = dfs(i + di, j + dj, c)
        totalArea += area
        totalPerimeter += perimeter
    }
    return totalArea to totalPerimeter
}
```

Let's break down how this works with an example:
```
AAA    Consider this 'A' region:
ABA    1. Start at any 'A' cell
AAA    2. DFS explores in all four directions
```

When exploring from a cell:
1. If we hit a boundary or different letter ('B'), we return (0,1)
    - The 0 means no new area
    - The 1 contributes to perimeter
2. If we hit an already visited cell, we return (0,0)
    - No new area or perimeter
3. For a valid unvisited cell:
    - Add 1 to area for current cell
    - Sum perimeters from all directions
    - Sum areas from all directions

### Part 2: Distinct Edge Counting

The second part introduces a more sophisticated edge counting mechanism:

```kotlin
val verticalTransitions = HashMap<Pair<Int, Int>, MutableList<Int>>()
val horizontalTransitions = HashMap<Pair<Int, Int>, MutableList<Int>>()
```

This structure tracks transitions between different plant types:
1. Vertical transitions store pairs of (x1,x2) -> list of y-coordinates
2. Horizontal transitions store pairs of (y1,y2) -> list of x-coordinates

Example of how transitions are recorded:
```
AAAA    Horizontal transitions between A and B:
BBBB    - (0,1) -> [0, 1, 2, 3]
```

The `numberOfContinuousSections` function is crucial:
```kotlin
fun List<Int>.numberOfContinuousSections(): Int {
    var result = 0
    for (i in 1..lastIndex) {
        if (this[i] - this[i - 1] > 1) {
            result++
        }
    }
    return result + 1
}
```

This function identifies distinct edges by:
1. Sorting transition points
2. Looking for gaps larger than 1 unit
3. Each continuous section represents one distinct edge

## Advanced Implementation Considerations

### Edge Cases and Corner Cases
The solution handles several tricky scenarios:
1. Regions with holes:
   ```
   AAAA
   ABBA   The 'A' region has both outer and inner perimeter
   AAAA
   ```
2. Diagonal connections (not considered connected):
   ```
   AB
   BA    These A's are not connected
   ```
3. Multiple regions of same type:
   ```
   AAA
   BBB   Two separate 'A' regions
   AAA
   ```

## Algorithmic Complexity Detailed Analysis

### Time Complexity Breakdown
- Grid traversal: $$O(N*M)$$ amortized
- DFS for each unvisited cell: $$O(1)$$ amortized
- Transition processing:
    - Sorting transitions: $$O(E*log(E))$$ where E is edge count
    - Processing continuous sections: $$O(E)$$
- Total: $$O(N*M + E*log(E))$$

### Space Complexity Components
1. Visited array: $$O(N*M)$$
2. Recursion stack: $$O(N*M)$$ worst case
3. Transition storage:
    - Vertical transitions: $$O(M*N)$$ worst case
    - Horizontal transitions: $$O(M*N)$$ worst case
4. Total: $$O(N*M)$$