# Day 5: Print Queue

## Intuition

This is fundamentally a problem about order verification and sorting. The rules X|Y simply state "X must come before Y" - it's a set of precedence rules, similar to checking if items in a list respect a given ordering.

For example, if we have rule "47|53", then in any valid sequence containing both 47 and 53, we must see 47 before 53. We don't need to think about paths or connections - just whether the relative positions of numbers in each sequence respect all applicable rules.

## Approach

1. **Parsing**
   ```kotlin
   fun List<String>.parse(): Pair<Set<Pair<Int, Int>>, List<List<Int>>>
   ```
    - Split input into rules and sequences at the empty line
    - Convert rules like "47|53" into pairs (47,53) meaning "47 must come before 53"
    - Convert sequences into lists of integers
    - Store rules in a Set for efficient lookup

2. **Sequence Validation**
   ```kotlin
   fun List<List<Int>>.partition(rules: Set<Pair<Int, Int>>)
   ```
    - For each sequence, check every pair of numbers in order
    - For positions i and j where j > i, look up if their ordering should follow a rule
    - If any pair violates their required ordering, the sequence is invalid
    - Use partition() to separate valid and invalid sequences

3. **Part 1 - Sum Middle Numbers of Valid Sequences**
    - Take only the valid sequences
    - For each sequence, find its middle element (at index size/2)
    - Sum all middle elements

4. **Part 2 - Fix and Sum Middle Numbers of Invalid Sequences**
    - Take the invalid sequences
    - Sort each sequence using the rules as comparison criteria
    - Find middle element of each corrected sequence
    - Sum all middle elements

The key insight is that we're just checking and enforcing ordering relationships between pairs of numbers, rather than dealing with any graph-like structure. The solution is simpler than I initially suggested - it's about verifying and correcting sequences based on a set of "before/after" rules.