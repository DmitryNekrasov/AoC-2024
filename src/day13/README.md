# Day 13: Claw Contraption

<p align="center">
  <img src="aoc-day-13.png"/>
</p>

## Intuition
This problem is fundamentally about solving systems of linear equations. Each claw machine presents us with two equations:
- For X-axis: aX × n + bX × m = targetX
- For Y-axis: aY × n + bY × m = targetY

where:
- n is the number of times button A is pressed
- m is the number of times button B is pressed
- aX, aY are the X and Y movements for button A
- bX, bY are the X and Y movements for button B
- targetX, targetY are the prize coordinates

## Approach
1. **Data Parsing**
    - Parse input into `Equation` data class containing movements and target coordinates
    - Each equation represents one claw machine's configuration

2. **Equation Solving**
    - Use substitution method to solve the system of equations:
    - From X equation: n = (targetX - m × bX) / aX
    - Substitute into Y equation:
        - aY × ((targetX - m × bX) / aX) + bY × m = targetY
        - Solve for m first, then calculate n

3. **Solution Verification**
    - Check if the calculated n and m values:
        - Are integers (using rounding with 0.1 addition for floating-point precision)
        - Satisfy both X and Y equations

4. **Token Calculation**
    - For each valid solution, calculate total tokens:
        - Each A press costs 3 tokens (n × 3)
        - Each B press costs 1 token (m × 1)
    - Sum up tokens for all solvable machines

## Time Complexity
- **Parsing**: $$O(N)$$ where N is the number of input lines
- **Solving**: $$O(M)$$ where M is the number of equations (claw machines)
- Overall complexity: $$O(N + M)$$

## Space Complexity
- $$O(M)$$ for storing the equations

## Mathematical Foundation
This solution leverages high school algebra concepts:
1. System of linear equations
2. Substitution method
3. Linear equation solving
4. Basic arithmetic operations with floating-point numbers

The clever part is handling the large numbers in Part 2 (10^13 offset) by treating it as an addition parameter, which doesn't affect the core equation-solving logic since it's just a constant offset to both sides of each equation.

## Key Insights
1. The problem may seem like it needs complex algorithms, but it's actually pure mathematics
2. Using Double for calculations helps handle precision issues
3. Adding 0.1 before converting to Long helps handle floating-point rounding errors
4. The solution elegantly handles both parts with the same code by using an optional offset parameter