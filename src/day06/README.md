# Day 6: Guard Gallivant

## Intuition
The problem essentially involves two main challenges:
1. Part 1: Simulating a guard's movement through a grid following specific rules and counting unique visited positions
2. Part 2: Finding positions where placing an obstacle would create a closed loop in the guard's path

## Approach for Part 1

1. Find the starting position by locating '^' in the input grid
2. Implement guard movement rules:
    - Store movement directions in a list `d` representing up, right, down, left (counterclockwise)
    - If there's an obstacle ahead, turn right (increment direction index)
    - Otherwise, move forward in current direction
3. Track visited positions using a special character ('&')
4. Keep moving until either:
    - Guard leaves the grid boundaries (return false)
    - Guard enters a position with same direction (found a loop, return true)
5. Count all positions marked as visited

Key implementation detail: The solution uses a clever way to track visited positions with directions using bitwise operations. Each cell in `visited` array stores which directions the guard has entered that cell from, using bits (`1 << direction`).

## Approach for Part 2

1. First run the original simulation to mark all positions the guard visits
2. For each visited position:
    - Try placing an obstacle there (set to '#')
    - Run the simulation again from start
    - If it results in a loop (returns true), increment counter
    - Reset the position back to '.' and continue
3. Return total count of positions that created loops

The solution is efficient because it:
- Uses a single character array for simulation
- Employs bitwise operations for direction tracking
- Only tries placing obstacles on the guard's original path
- Reuses the same traverse function for both parts

## Complexity

**Time Complexity:**
- Part 1: $$O(n * m)$$ where `n * m` is the grid size
- Part 2: $$O(k * n * m)$$ where `k` is the number of visited positions in original path

**Space Complexity:** $$O(n * m)$$ for the visited array