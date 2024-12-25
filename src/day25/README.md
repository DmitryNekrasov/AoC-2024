# Day 25: Code Chronicle

## Intuition
The problem simulates a virtual lock-and-key system where we need to find compatible pairs. A lock and key are compatible if their combined heights at each position don't exceed the total available space (7 units). The key insight is that we can convert both locks and keys into arrays of heights and then compare them position by position.

## Approach

1. **Parsing the Input**
    - The solution first parses the input into groups of strings using `parse()`
    - Each blank line indicates the start of a new lock or key schematic
    - Each schematic is converted into a list of strings

2. **Height Conversion**
    - The `toHeights()` function converts each schematic into an array of integers
    - It counts the number of '#' characters in each column
    - For locks, it counts from top to bottom
    - For keys, it counts from bottom to top

3. **Compatibility Check**
    - The `isCompatibleWith` infix function checks if a lock and key pair is compatible
    - It zips the height arrays together and checks if each pair of heights sums to ≤ 7
    - If any column's sum exceeds 7, the lock and key are incompatible

4. **Solution Steps**
    - Separate input into locks and keys based on whether the first row starts with '#'
    - Convert all schematics to height arrays
    - Try every possible lock-key combination
    - Count the number of compatible pairs

## Complexity

**Time Complexity**: $$O(L * K * N)$$
- L = number of locks
- K = number of keys
- N = width of the schematics
- We check each lock against each key, and for each pair, we compare N positions

**Space Complexity**: $$O(L * N + K * N)$$
- We store height arrays for all locks and keys
- Each height array has size N
- Total space is proportional to the number of locks and keys times the width

The solution efficiently handles the problem by converting the 2D visual representation into 1D height arrays, making the compatibility checks straightforward and fast.