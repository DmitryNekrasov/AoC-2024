## Intuition

The puzzle presents two lists of numbers that need to be compared in different ways:

**Part 1 - Distance Calculation:**
- The key insight is that we want to compare the numbers in a position-wise manner after sorting both lists
- This ensures we're matching smallest with smallest, second-smallest with second-smallest, etc.
- It's effectively measuring how "different" the lists are when aligned optimally

**Part 2 - Similarity Score:**
- This part completely changes the perspective - instead of position-wise comparison, we're looking at frequency matching
- Each number in the left list acts as a multiplier against its frequency in the right list
- This measures how "similar" the lists are in terms of shared values, regardless of position

## Approach

**Part 1: Total Distance**
1. Helper function `splitIntoTwoLists()`:
    - Parses input strings into two separate lists of integers
    - Uses fold to accumulate numbers into two lists

2. Main solution `part1()`:
    - Sort both lists to align smallest-to-largest
    - Zip the sorted lists to create pairs
    - Calculate absolute difference for each pair
    - Sum all differences

**Part 2: Similarity Score**
1. Create a frequency map of the right list using HashMap
    - Key: number
    - Value: frequency of occurrence

2. For each number in the left list:
    - Look up its frequency in the right list (0 if not present)
    - Multiply the number by its frequency
    - Sum all these products

## Complexity

- Part 1: $$O(n * log(n))$$ due to sorting
- Part 2: $$O(n)$$ for building frequency map and calculating sum