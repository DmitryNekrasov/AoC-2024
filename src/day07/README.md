# Day 7: Bridge Repair

## Intuition

This puzzle is essentially about evaluating mathematical expressions with different operators under specific rules:
1. Part 1 deals with only `+` and `*` operators
2. Part 2 adds a concatenation operator `||`
3. The key constraint is that operators are evaluated left-to-right (no operator precedence)
4. The numbers must be used in the given order

## Approach Part 1

The solution uses recursion to try all possible combinations of operators:

1. For each line, parse it into a target number and a list of values
    - Example: "190: 10 19" → target=190, values=[10, 19]

2. The `check` function uses recursive backtracking to:
    - Start with the first number
    - For each position between numbers, try both:
        - Adding the next number
        - Multiplying the next number
    - Continue until all numbers are used
    - Return true if any combination reaches the target value

3. Sum up the target values of all valid equations

## Approach Part 2

The solution switches to a more systematic approach for handling three operators:

1. Generate all possible operator combinations:
    - For n numbers, need n-1 operators
    - Each position can be '+', '*', or '|'
    - Uses recursive function `generateOperators` to build all combinations
    - Caches results for efficiency since same-length sequences are reused

2. Evaluate expressions:
    - `perform` function takes values and operators
    - Processes left-to-right, applying each operator
    - Special handling for concatenation using string conversion
    - Example: [17, 8, 14] with ['|', '+'] → "17" || "8" + 14 = 178 + 14 = 192

3. For each equation:
    - Generate all possible operator combinations
    - Try each combination to see if it produces the target value
    - Sum up targets of valid equations

## Complexity

**Part 1:**

Time Complexity: $$O(2^n)$$
- For each position between n numbers, we try 2 operators (+, *)
- This creates a binary tree of depth n-1
- We must explore all possibilities until we find a match or exhaust options
- Therefore, total number of paths is `2^(n-1)` ≈ $$O(2^n)$$

Space Complexity: $$O(n)$$
- The recursion stack can go n levels deep
- At each level, we only store constant extra space
- Therefore space usage is proportional to depth = $$O(n)$$

**Part 2:**

Time Complexity: $$O(3^n * n)$$
- For n numbers, we need n-1 operators
- Each position can have 3 possible operators (+, *, ||)
- Total number of operator combinations = `3^(n-1)`
- For each combination, we evaluate the expression which takes $$O(n)$$ time
- Therefore total time is `O(3^(n-1) * n)` ≈ $$O(3^n * n)$$

Space Complexity: $$O(3^n * n)$$
- The cache stores all possible operator combinations:
    * For length `n-1`, we store `3^(n-1)` combinations
    * Each combination is of length `n-1`
    * Total space for cache = `O(3^(n-1) * (n-1))`
- The evaluation process uses $$O(n)$$ additional space
- Therefore total space is $$O(3^n * n)$$