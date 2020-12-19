import java.io.File
import kotlin.math.pow

data class Instruction(val address: ULong, val value: ULong)

fun applyMask(mask: String, value: ULong): List<ULong> {
  // get the highest possible address
  val highestAddr = mask.replace('X', '1').toULong(2) or value

  // generate addresses by successively subtracting powers of two where the Xs were
  val powersOfTwo = mask.reversed().withIndex().filter{ it.value == 'X' }.map{ it.index }

  return powersOfTwo.fold(listOf(highestAddr)){ acc, pow ->
    acc + acc.map{ it - 2.toDouble().pow(pow).toULong() }
  }
}

val filename = "input"
val reversedLines = File(filename).readLines().reversed()

val maskRegex = Regex("""mask = ([X10]+)""")
val instrRegex = Regex("""mem\[(\d+)\] = (\d+)""")

//println(applyMask("000000000000000000000000000000X1001X", 42.toULong()))

val instructions = reversedLines.map{ line ->
  when (line.startsWith("mask")) {
    true -> maskRegex.matchEntire(line)!!.groupValues[1]
    else -> {
      val (addr, value) = instrRegex.matchEntire(line)!!.groupValues.slice(1..2).map{ it.toULong() }
      Instruction(addr,value)
    }
  }
}

val masksWithIndices = instructions.withIndex().filter{ it.value !is Instruction }
val instrRanges = (listOf(-1) + masksWithIndices.map{ it.index }).zipWithNext().map{ (a, b) -> (a+1.. b-1) }
val instrChunks = instrRanges.map{ instructions.slice(it) }
val instructionsWithMasks = instrChunks.zip(masksWithIndices.map{ it.value }) as List<Pair<List<Instruction>, String>>

val memory: Map<ULong, ULong> = instructionsWithMasks.fold(mapOf()){ acc, (instructions, mask) ->

  (instructions as List<Instruction>).fold(acc){ acc, (addr, value) ->

    val decodedAddresses = applyMask(mask as String, addr)
    println("${decodedAddresses.size} decoded addresses")
    decodedAddresses.fold(acc) { acc, decodedMemAddr ->
      if (acc.containsKey(decodedMemAddr)){
        acc
      } else {
        acc + mapOf(decodedMemAddr to value)
      }
    }
  }
}

println(memory.values.sum())
