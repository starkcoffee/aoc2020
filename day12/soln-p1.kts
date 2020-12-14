import java.io.File
import kotlin.math.abs

data class PositionVector(val unitsEast: Int, val unitsNorth: Int, val heading: Int) {
  
  fun addToEast(units: Int): PositionVector {
    return PositionVector(unitsEast + units, unitsNorth, heading)
  }

  fun addToNorth(units: Int): PositionVector {
    return PositionVector(unitsEast, unitsNorth + units, heading)
  }

  fun addToHeading(degrees: Int): PositionVector {
    return PositionVector(unitsEast, unitsNorth, heading + degrees)
  }
}

fun parseInstruction(line: String): Pair<Char, Int> {
  val (action, num) = Regex("""(\w)(\d+)""").matchEntire(line)!!.destructured
  return Pair(action[0], num.toInt())
}

val filename = "input"
val lines = File(filename).readLines()

val instructions = lines.map{ parseInstruction(it) }

val startVector = PositionVector(0, 0, 0)

val endVector = instructions.fold(startVector){ acc, instr ->
  when (instr.first) {
    'N' -> acc.addToNorth(instr.second)
    'S' -> acc.addToNorth(0-instr.second)
    'E' -> acc.addToEast(instr.second)
    'W' -> acc.addToEast(0-instr.second)
    'L' -> acc.addToHeading(instr.second)
    'R' -> acc.addToHeading(0-instr.second)
    'F' -> when ((acc.heading / 90 % 4+ 4)%4) {
      0 -> acc.addToEast(instr.second)
      1 -> acc.addToNorth(instr.second)
      2 -> acc.addToEast(0-instr.second)
      3 -> acc.addToNorth(0-instr.second)
      else -> acc
    }
    else -> acc
  }
}

println(endVector)
println(abs(endVector.unitsEast) + abs(endVector.unitsNorth))
