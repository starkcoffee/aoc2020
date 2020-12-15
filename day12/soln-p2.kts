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

  fun rotateAround(degrees: Int): PositionVector {
    return when ((degrees % 360 + 360)%360) {
      90 -> PositionVector(-this.unitsNorth, this.unitsEast, 0)
      180 -> PositionVector(-this.unitsEast, -this.unitsNorth, 0)
      270 -> PositionVector(this.unitsNorth, -this.unitsEast, 0)
      else -> this
    }
  }

  fun moveShipBy(ship: PositionVector, times: Int): PositionVector {
    return PositionVector(ship.unitsEast + times*this.unitsEast, 
      ship.unitsNorth + times*this.unitsNorth, 0)
  }
}

fun parseInstruction(line: String): Pair<Char, Int> {
  val (action, num) = Regex("""(\w)(\d+)""").matchEntire(line)!!.destructured
  return Pair(action[0], num.toInt())
}

val filename = "input"
val lines = File(filename).readLines()

val instructions = lines.map{ parseInstruction(it) }

val waypoint = PositionVector(10, 1, 0)
val startVector = PositionVector(0, 0, 0)

val end = instructions.fold(Pair(startVector, waypoint)){ (pos,wp), instr ->
  when (instr.first) {
    'N' -> Pair(pos, wp.addToNorth(instr.second))
    'S' -> Pair(pos, wp.addToNorth(0-instr.second))
    'E' -> Pair(pos, wp.addToEast(instr.second))
    'W' -> Pair(pos, wp.addToEast(0-instr.second))
    'L' -> Pair(pos, wp.rotateAround(instr.second))
    'R' -> Pair(pos, wp.rotateAround(0-instr.second))
    'F' -> Pair(wp.moveShipBy(pos, instr.second), wp)
    else -> Pair(pos, wp)
  }
}

println(end)
println(abs(end.first.unitsEast) + abs(end.first.unitsNorth))
