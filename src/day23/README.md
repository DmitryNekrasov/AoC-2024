# Day 23: LAN Party

## Intuition
The problem presents two graph theory challenges:
1. Part 1 requires finding triangles (3-vertex cliques) in an undirected graph where at least one vertex starts with 't'
2. Part 2 requires finding the maximum clique in the graph

## Approach

### Part 1: Finding Triangles
The solution uses a straightforward approach to find triangles:
1. Iterate through each vertex that starts with 't'
2. For each such vertex, look at all pairs of its neighbors
3. If these neighbors are also connected to each other, we've found a triangle
4. Store triangles in a HashSet to avoid duplicates
5. Sort the vertices in each triangle to ensure unique representation

### Part 2: Maximum Clique
The solution implements the [Bron-Kerbosch algorithm](https://en.wikipedia.org/wiki/Bron%E2%80%93Kerbosch_algorithm) with pivot optimization to find all maximal cliques:
1. Uses recursion with three sets:
    - R: current clique being built
    - P: prospective vertices that could extend the clique
    - X: excluded vertices that have already been processed
2. Uses pivot optimization to reduce the branching factor
3. Stores all maximal cliques and returns the largest one (by number of vertices)
4. Formats the result as a comma-separated string of sorted vertex names

## Complexity

### Part 1
- Time: $$O(V * D^2)$$ where:
    - V is the number of vertices starting with 't'
    - D is the maximum degree of any vertex
- Space: $$O(T)$$ where T is the number of triangles found

### Part 2 (Bron-Kerbosch with pivot)
- Time: $$O(3^(n/3))$$ in the worst case, where n is the number of vertices
- Space: $$O(n)$$ for the recursion stack and sets