import java.io.File

data class Mask(val mask: String) {
  val andMask = mask.replace('X','1').toULong(2)
  val orMask = mask.replace('X','0').toULong(2)
}

fun applyMasks(mask: Mask, value: ULong): ULong {
  return (mask.andMask and value) or mask.orMask
}

val filename = "input"
val lines = File(filename).readLines()

val maskRegex = Regex("""mask = ([X10]+)""")
val instrRegex = Regex("""mem\[(\d+)\] = (\d+)""")


val instructions = lines.map{ line -> when {
    maskRegex.matches(line) -> Mask(maskRegex.matchEntire(line)!!.groupValues[1])
    else -> {
      val (memAddrStr, valueStr) = instrRegex.matchEntire(line)!!.destructured
      Pair(memAddrStr.toULong(), valueStr.toULong())
    }
  }
}

val maskIndices = instructions.withIndex().filter{ it.value is Mask }.map { it.index }

maskIndices.zipWithNext{a, b -> a to b-1 }

println(maskIndices)

/*
val memory: Map<ULong, ULong> = instructions.fold(mapOf()){ acc, instr ->
  acc + mapOf(instr.first to applyMasks(andMask, orMask, instr.second))
}

println(memory.values.sum())
*/
