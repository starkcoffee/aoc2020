import java.io.File

enum class Code { NOP, ACC, JMP }
data class Instruction(val code: Code, val arg: Int) 

fun executeInstructionAt(index: Int, acc: Int, visitedIndices: Set<Int>, instructions: List<Instruction>): Pair<Int,Int> {
  if (index in visitedIndices) return Pair(acc, index)
  if (index >= instructions.size) return Pair(acc, index)

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

fun flipAt(index: Int, instructions: List<Instruction>): List<Instruction> {
  return instructions.map{ instr -> 
    if (instructions.indexOf(instr) == index){
      when (instr.code) {
        Code.JMP -> Instruction(Code.NOP, instr.arg) 
        Code.NOP -> Instruction(Code.JMP, instr.arg)
        Code.ACC -> instr
      }
    } else {
      instr
    }
  }
}

val jmpsOrNops = instructions.filter{ it.code != Code.ACC }.map{ instructions.indexOf(it)}.iterator()

fun flipUntilItWorks(jmpsOrNops: Iterator<Int>, instructions: List<Instruction>): Pair<Int, Int> {
  val flipped = flipAt(jmpsOrNops.next(), instructions)
  val (acc, lastIndex) = executeInstructionAt(0, 0, setOf(), flipped)
  return when (lastIndex >= instructions.size) {
    true -> Pair(acc, lastIndex) 
    false -> flipUntilItWorks(jmpsOrNops, instructions)
  }
}

val (acc, lastIndex) = flipUntilItWorks(jmpsOrNops, instructions)
println(lastIndex)
println("acc is ${acc}")



