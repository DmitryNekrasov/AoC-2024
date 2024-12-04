# Day 2: Red-Nosed Reports

## Intuition

The problem essentially asks us to validate sequences of numbers based on two main criteria. In Part 1, we're looking for strictly monotonic sequences (either increasing or decreasing) where adjacent numbers differ by 1-3. In Part 2, we add flexibility by allowing one number to be removed to achieve this pattern.

The key insight is that we can break this down into two clear checks:
1. Ensuring consistent direction (all increasing or all decreasing)
2. Validating the magnitude of differences between adjacent numbers

## Approach

**Part 1:**
1. For each sequence, we first determine its direction by comparing the first two numbers
    - If they're equal, we can immediately fail the sequence (non-monotonic)
    - The comparison result (-1 or 1) gives us the expected direction

2. We then iterate through adjacent pairs, checking two things:
    - The direction matches our initial direction
    - The absolute difference between numbers is ≤ 3

3. If any check fails, the sequence is unsafe. Otherwise, it's safe.

**Part 2:**
1. The key addition is that we can now "skip" one number to make a sequence valid
2. We implement this by trying to remove each number in the sequence:
    - For each index we want to ignore, we check if the remaining sequence is valid
    - When checking the sequence, we need to handle three special cases:
        - Ignoring the first number (use second and third to determine direction)
        - Ignoring the second number (use first and third)
        - Ignoring any other number (use first and second)

3. A sequence is now safe if either:
    - It's safe without removing any numbers (Part 1 logic)
    - It becomes safe after removing exactly one number

I'll analyze the time and space complexity and create a detailed markdown documentation.

## Complexity

### Time Complexity

#### Part 1: $$O(n * m)$$
- where n is the number of sequences
- where m is the length of each sequence
- For each sequence, we make one pass through the numbers
- Each comparison operation is $$O(1)$$

#### Part 2: $$O(n * m²)$$
- For each sequence, we try removing each number (m iterations)
- For each removed number, we check if remaining sequence is valid (m iterations)
- This gives us a nested loop structure for each sequence

### Space Complexity: $$O(n * m)$$
- The input matrix storage requires $$O(n * m)$$ space
- No additional data structures are used
- All operations are performed in-place