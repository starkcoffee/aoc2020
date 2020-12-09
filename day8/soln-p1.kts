import java.io.File

enum class Code { NOP, ACC, JMP }
data class Instruction(val code: Code, val arg: Int) 

fun executeInstructionAt(index: Int, acc: Int, visitedIndices: Set<Int>, instructions: List<Instruction>): Int {
  if (index in visitedIndices) return acc

  println(index)

  val next = instructions[index]

  return when(next.code) {
    Code.NOP -> executeInstructionAt(index + 1, acc, visitedIndices + setOf(index), instructions)
    Code.ACC -> executeInstructionAt(index + 1, acc+next.arg, visitedIndices + setOf(index), instructions)
    Code.JMP -> executeInstructionAt(index + next.arg, acc, visitedIndices + setOf(index), instructions)
  }
}

val filename = "input"
val lines = File(filename).readLines()

val instructions = lines.map{ it.split(' ') }.map{ Instruction(Code.valueOf(it[0].toUpperCase()), it[1].toInt()) }

val acc = executeInstructionAt(0, 0, setOf(), instructions)

println("acc is ${acc}")


