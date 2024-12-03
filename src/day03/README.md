## Intuition

This problem is fundamentally about parsing text according to specific patterns while handling corrupted data. The core challenges are:

1. Identifying valid multiplication instructions (`mul(X,Y)`) amidst corrupted text
2. Managing state changes from `do()` and `don't()` commands in Part 2
3. Ignoring invalid or corrupted instructions

The key insight is that this can be elegantly solved using a finite state machine (automaton) approach, which is particularly good at pattern recognition in text streams.

## Approach

### State Machine Design

The solution implements a deterministic finite automaton (DFA) with the following key characteristics:

1. **States**: The automaton uses numeric states (0-17) to track progress through pattern matching:
    - State 0: Initial/reset state
    - States 1-3: Matching "mul"
    - States 4-8: Handling number parsing and multiplication
    - States 9-17: Managing do/don't instructions

2. **Transitions**: The `automata` HashMap defines valid transitions between states:
    - Uses character inputs to determine next state
    - Special handling for digits by mapping them to a placeholder character ('q')
    - Returns to state 0 on invalid transitions (error recovery)

<p align="center">
  <img src="automata.png" alt="Work of plugin"/>
</p>

### Pattern Recognition Process

The solution processes the input character by character with several key mechanisms:

1. **Number Accumulation**:
    - Uses StringBuilder objects to accumulate digits for both numbers
    - State 5 accumulates first number
    - State 7 accumulates second number
    - Clears buffers when returning to state 0

2. **Multiplication Execution**:
    - Occurs in state 8 (closing parenthesis)
    - Converts accumulated strings to integers and multiplies
    - Only executes if in Part 1 or if `doit` flag is true

3. **State Control**:
    - Tracks enabled/disabled state with boolean `doit` variable
    - Updates on complete do() (state 12) or don't() (state 17) instructions
    - Maintains most recent instruction's effect

### Key Implementation Details

1. **Error Handling**:
    - Invalid characters automatically reset state to 0
    - Incomplete patterns are naturally ignored
    - Corrupted instructions don't affect the state machine's integrity

2. **Digit Handling**:
    - Maps all digits to a single character ('q') in transition table
    - Allows for handling variable-length numbers without expanding state space

3. **Part 2 Integration**:
    - Reuses same state machine with additional states
    - Uses `isPartOne` parameter to toggle behavior
    - Maintains stateful processing with `doit` flag

The solution is efficient with $$O(n)$$ time complexity where `n` is the total length of input strings, as it processes each character exactly once with constant-time operations at each step.