# Day 22: Monkey Market

## Intuition
This puzzle involves understanding and implementing a sequence generator based on bitwise operations and tracking patterns in the generated numbers.

For Part 1, the key insight is that we need to:
1. Follow the exact sequence of operations (multiply, XOR, modulo) to generate the next secret number
2. Repeat this process 2000 times for each initial number
3. Sum up the final numbers

For Part 2, the challenge shifts to pattern recognition where we need to:
1. Convert secret numbers to their ones digit to get prices
2. Find sequences of four consecutive price changes
3. Identify which sequence of changes leads to the highest total price across all buyers

## Approach

### Part 1: Secret Number Generation
1. Implemented helper functions for the core operations:
    - `mixInto`: Performs bitwise XOR between two numbers
    - `prune`: Applies modulo 16777216 to keep numbers in range
    - `nextSecretNumber`: Combines the three required steps:
        * Multiply by 64, mix, prune
        * Divide by 32, mix, prune
        * Multiply by 2048, mix, prune

2. Used `fold` operation to generate the 2000th number for each initial value:
```kotlin
nums.sumOf { num -> 
    (0..<2000).fold(num) { acc, _ -> acc.nextSecretNumber() } 
}
```

### Part 2: Price Pattern Analysis
1. Generate all secret numbers and convert to prices (ones digit)
2. Calculate price changes between consecutive prices
3. Use sliding window of size 4 to find all possible change patterns
4. Track the maximum achievable price for each pattern across all buyers
5. Find the pattern that yields the highest total price

The solution uses HashMaps to efficiently store and look up patterns:
```kotlin
val changes = HashMap<List<Int>, Int>()
for (num in nums) {
    val changesForNum = HashMap<List<Int>, Int>()
    // ... process patterns for each number
}
```

## Complexity

### Part 1
- Time Complexity: $$O(n * m)$$ where:
    * n is the number of initial values
    * m is the number of iterations (2000)
- Space Complexity: $$O(1)$$ as we only store the current number

### Part 2
- Time Complexity: $$O(n * m)$$ where:
    * n is the number of initial values
    * m is the number of iterations (2000)
    * We also have a windowing operation but it's bounded by m
- Space Complexity: $$O(m)$$ for storing:
    * The sequence of secret numbers
    * The HashMap of patterns and their corresponding prices