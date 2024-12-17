# Day 17: Chronospatial Computer

<p align="center">
  <img src="aoc-day-17.png"/>
</p>

## Program Architecture

### Register Operations
The program operates on three registers:
- Register A: Main data source
- Register B: Working register for transformations
- Register C: Temporary storage for division results

### Instructions Breakdown (8 opcodes)
```
0 (adv): A = A / (2^operand)      // Divides A by power of 2
1 (bxl): B = B XOR operand        // XOR B with literal
2 (bst): B = operand % 8          // Set B to operand mod 8
3 (jnz): if A != 0: goto operand  // Conditional jump
4 (bxc): B = B XOR C              // XOR B with C
5 (out): output operand % 8       // Output value
6 (bdv): B = A / (2^operand)      // Like adv but to B
7 (cdv): C = A / (2^operand)      // Like adv but to C
```

## Input Program Analysis

My specific input program:
```
2,4,1,2,7,5,1,7,4,4,0,3,5,5,3,0
```

Let's analyze it step by step:

1. Initial Pattern:
```kotlin
step1) B = A % 8              // Extract lowest 3 bits
step2) B = B xor 2            // Transform B
step3) C = A / (1 << B)       // Divide A by 2^B
step4) B = B xor 7            // Transform B again
step5) B = B xor C            // Mix B with division result
step6) A = A / 8              // Move to next 3 bits
step7) out B                  // Output transformed value
step8) if A != 0; goto step1  // Loop until A is 0
```

## Part 1: Implementation Details

```kotlin
fun perform(aReg: Long, bReg: Long, cReg: Long, program: List<Int>): List<Int> {
    var aReg = aReg
    var bReg = bReg
    var cReg = cReg
    val output = mutableListOf<Int>()
    var pc = 0
```

Key implementation features:
1. Uses mutable variables for registers
2. Tracks program counter (pc)
3. Collects output in a list
4. Processes combo operands lazily:

```kotlin
val comboOperand = lazy {
    when (literalOperand) {
        in 0..3 -> literalOperand.toLong()
        4 -> aReg
        5 -> bReg
        6 -> cReg
        else -> throw RuntimeException("Should not reach here")
    }
}
```

## Part 2: Deep Dive

The backtracking solution:

```kotlin
fun backtrack(a: Long = 0L, index: Int = program.lastIndex): Long {
   if (index < 0) return a
   val result = mutableListOf<Long>()
   for (mod8 in 0..7) {
      val performResult = perform(a + mod8, 0L, 0L, program)
      if (performResult.first() == program[index]) {
         result += backtrack((a + mod8) * 8, index - 1)
      }
   }
   return result.minOrNull() ?: Long.MAX_VALUE
}
```

### How Backtracking Works:
1. Starts from the last program index
2. For each position:
    - Tries all possible 3-bit values (0-7)
    - Simulates the program's transformations
    - If result matches desired output, recurses to previous position
3. Builds solution from right to left by:
    - Multiplying by 8
    - Adding the current 3-bit value

### Why It's Input-Specific:
1. Program always outputs B register
2. A is divided by 8 each time

## Testing Strategy

1. Verify with example case:
```kotlin
val expected = "4,6,3,5,6,3,5,2,1,0"
val actual = part1(aReg, bReg, cReg, program)
assertEquals(expected, actual)
```

2. Validate Part 2 solution:
```kotlin
assertEquals(program, perform(ans, 0L, 0L, program))
```