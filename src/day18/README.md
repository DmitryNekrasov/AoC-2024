# Day 18: RAM Run

## Intuition
The core problem is a path-finding challenge in a corrupting grid, where we need to:
1. Find the shortest path from start (0,0) to end (n-1,n-1) while avoiding corrupted cells in Part 1
2. Find the first point that makes the path impossible in Part 2

The problem can be solved using:
- Breadth-First Search (BFS) to find the shortest path
- Binary search to efficiently find the critical point where the path becomes impossible

## Approach

### Part 1: Finding Shortest Path
The solution uses BFS to find the shortest path through the grid after simulating the first 1024 bytes falling:

1. Grid Creation and Corruption:
```kotlin
val grid = Array(n) { CharArray(n) { '.' } }
for (i in 0..<limit) {
    val (x, y) = points[i]
    grid[y][x] = '#'
}
```
- Initialize an n×n grid with safe cells ('.')
- Mark the first 'limit' positions as corrupted ('#')

2. BFS Implementation:
```kotlin
val queue: Queue<List<Int>> = LinkedList()
queue.offer(listOf(0, 0, 0))  // x, y, distance
```
- Use a queue to track positions to explore
- Each position stores coordinates and current distance
- Explore in four directions (up, down, left, right)
- Track visited cells to avoid cycles

3. Path Finding:
```kotlin
for ((dx, dy) in listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)) {
    val nextX = x + dx
    val nextY = y + dy
    if (nextX in 0..<n && nextY in 0..<n && 
        grid[nextY][nextX] == '.' && 
        !visited[nextY][nextX]) {
        // ... process valid moves
    }
}
```
- Check boundary conditions
- Ensure next cell is safe
- Avoid revisiting cells

### Part 2: Finding Critical Point
The solution uses binary search to efficiently find the point where the path becomes impossible:

1. Binary Search Implementation:
```kotlin
fun binSearch(left: Int = 0, right: Int = points.size): Int {
    if (left >= right) return right
    val mid = left + (right - left) / 2
    return if (distance(n, mid, points) == -1) 
        binSearch(left, mid) 
    else 
        binSearch(mid + 1, right)
}
```
- Use binary search to find the transition point
- If path exists at mid point, search in upper half
- If path doesn't exist, search in lower half

2. Critical Point Identification:
- Binary search returns the index after the critical point
- Subtract 1 to get the exact point that blocks the path
- Return coordinates in required format

## Complexity Analysis

### Part 1: Finding Shortest Path
- Time Complexity: $$O(n^2)$$
    - BFS visits each cell at most once
    - Each cell has constant number of neighbors (4)
    - Grid size is `n * n`
- Space Complexity: $$O(n^2)$$
    - Grid storage: $$O(n^2)$$
    - Visited array: $$O(n^2)$$
    - Queue: $$O(n^2)$$ in worst case

### Part 2: Finding Critical Point
- Time Complexity: $$O(log(p) * n^2)$$
    - Binary search takes $$O(log(p))$$ iterations, where `p` is number of points
    - Each iteration runs BFS taking $$O(n^2)$$
- Space Complexity: $$O(n^2)$$
    - Same as Part 1
    - Binary search uses $$O(1)$$ additional space

The overall solution is efficient for the given constraints (n=71) and handles both parts effectively using standard graph algorithms combined with binary search optimization.