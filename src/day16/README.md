# Day 16: Reindeer Maze

<p align="center">
  <img src="aoc-day-16.png"/>
</p>

The core insight is that this is fundamentally a graph problem, but with an interesting twist - the state of a reindeer isn't just its position, but also its direction. This leads to a clever graph transformation that makes the problem solvable using standard shortest path algorithms.

## Intuition

When we think about the possible moves from any position, the reindeer can either move forward (cost 1) or rotate 90 degrees clockwise or counterclockwise (cost 1000). The fact that we can only move forward in our current direction and need to explicitly rotate makes this different from a typical maze where we can freely move in any direction. This suggests we need to incorporate direction into our state representation.

## Approach

The solution uses a brilliant graph transformation where each cell in the maze becomes four vertices in our graph - one for each possible direction (North, East, South, West). Here's how it works:

1. State Representation: Each vertex in our transformed graph is represented by an ID that encodes both position and direction:
```kotlin
fun List<String>.id(i: Int, j: Int, direction: Int) = (i * first().length + j) * 4 + direction
```
This creates a unique identifier for each combination of position and direction.

2. Graph Construction: For each non-wall cell, we create edges representing:
    - Moving forward in the current direction (cost 1)
    - Rotating clockwise or counterclockwise (cost 1000)

The graph building process uses a BFS to visit all reachable cells and creates appropriate edges for each possible move.

3. Finding Shortest Path: Once we have this transformed graph, we can use Dijkstra's algorithm to find the shortest path. However, there's another clever addition - we're not just tracking distances, but also maintaining a list of parent nodes for each vertex to help with part 2:
```kotlin
val d = IntArray(vertexNumber) { INF }  // distances
val p = Array(vertexNumber) { mutableListOf<Int>() }  // parents
```

4. Part 2 Solution: To find all cells that are part of any optimal path, we perform a BFS starting from the end vertex, following parent pointers backward. This gives us all vertices that can be part of an optimal solution.

## Complexity

Time Complexity:
- Graph Construction: $$O(N*M)$$ where N and M are the dimensions of the maze
- Dijkstra's Algorithm: $$O((V + E) * log(V))$$ where V is the number of vertices (4*N*M) and E is the number of edges
- BFS for Part 2: $$O(V + E)$$

Space Complexity:
- Graph Storage: $$O(V + E)$$
- Distance Array: $$O(V)$$
- Parent Arrays: $$O(V)$$

What makes this solution particularly elegant is how it transforms a seemingly complex problem involving direction and rotation into a standard shortest path problem through clever state representation. The addition of parent tracking for each vertex efficiently solves part 2 without needing to store or compute all possible optimal paths explicitly.