# Day 10: Hoof It

## Intuition
The problem can be broken down into two key parts:
1. Finding all possible paths from a trailhead (height 0) that reach height 9
2. For each trailhead, calculating:
    - Part 1: Number of unique endpoints (score)
    - Part 2: Total number of distinct paths (rating)

The nature of the problem (finding all possible paths with specific constraints) suggests a depth-first search (DFS) approach would be effective, as we need to explore all possible routes that satisfy the increasing height requirement.

## Approach
The solution implements a clever hybrid approach that solves both parts in a single DFS traversal:

1. **Trailhead Identification**:
    - Iterate through the grid to find all positions with height '0'
    - Each such position serves as a starting point for path exploration

2. **Path Exploration (DFS)**:
    - For each trailhead, perform DFS that:
        - Only moves in four directions (up, down, left, right)
        - Only proceeds if the next height is exactly one more than current height
        - Tracks both unique endpoints (for score) and total paths (for rating)

3. **State Tracking**:
    - Uses a HashSet to track unique endpoints (height 9 positions)
    - Maintains a count of total paths for the rating

4. **Key Optimizations**:
    - Combines both part 1 and part 2 calculations in a single traversal
    - Uses a HashSet for O(1) endpoint lookups
    - Returns a Pair to handle both score and rating simultaneously

## Time Complexity

- **Grid Traversal**: $$O(N*M)$$ for finding trailheads
- **DFS Exploration**: For each trailhead:
    - Worst case: $$O(4^H)$$ where H is the maximum path length
    - In practice, much better due to height constraints

Overall Complexity: $$O(N * M * 4^H)$$ where:
- N = grid height
- M = grid width
- H = maximum possible path length

Space Complexity: $$O(H)$$ for the recursion stack + $$O(K)$$ for storing endpoints, where K is the number of reachable height-9 positions