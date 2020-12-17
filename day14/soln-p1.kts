import java.io.File

data class Mask(val mask: String) {
  val andMask = mask.replace('X','1').toULong(2)
  val orMask = mask.replace('X','0').toULong(2)
}

fun applyMask(mask: Mask, value: ULong): ULong {
  return (mask.andMask and value) or mask.orMask
}

val filename = "input"
val reversedLines = File(filename).readLines().reversed()

val maskRegex = Regex("""mask = ([X10]+)""")
val instrRegex = Regex("""mem\[(\d+)\] = (\d+)""")

val memory: Map<ULong, ULong> = reversedLines.withIndex().filter{ instrRegex.matches(it.value) }
  .fold(mapOf()){ acc, (i, line) ->

  val (memAddr, value) = instrRegex.matchEntire(line)!!.groupValues.slice(1..2).map{ it.toULong() }

  if (acc.containsKey(memAddr)){
    acc
  } else {
    val maskLine: String = reversedLines.slice(i+1..reversedLines.size-1).find{ maskRegex.matches(it) }!!
    val mask = Mask(maskRegex.matchEntire(maskLine)!!.groupValues[1])
    acc + mapOf(memAddr to applyMask(mask, value))
  }
}

println(memory.values.sum())
