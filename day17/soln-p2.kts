import java.io.File

data class Coord(val x: Int, val y: Int, val z: Int, val w: Int)

val filename = "input"
val lines = File(filename).readLines()

val activeCubes = lines.withIndex().flatMap{ (x, line) 
  -> line.withIndex().filter{ it.value == '#' }.map{ (y, _) -> Coord(x,y,0,0)}}.toSet()

fun getNeighbouringCoords(coord: Coord): Set<Coord> {
  return (-1..1).flatMap{ w -> 
    (-1..1).flatMap{ z -> 
    (-1..1).flatMap{ y -> 
    (-1..1).map{ x -> Coord(coord.x+x, coord.y+y, coord.z+z, coord.w+w) }}}}
    .filter { it != coord }
    .toSet()
}

fun nextActiveCubes(actives: Set<Coord>): Set<Coord> {
  val nextCoords = actives + actives.fold(setOf()){ acc, coord -> acc + getNeighbouringCoords(coord) }

  return nextCoords.fold(setOf()){ acc, coord -> 
    val activeNeighbours = getNeighbouringCoords(coord).intersect(actives).size
    if (coord in actives && activeNeighbours in (2..3)) {
      acc + coord  
    } else {
      if(activeNeighbours == 3)  {
        acc + coord
      } else {
        acc
      }
    }
  }
}

val finalCubes = (1..6).fold(activeCubes){ acc, _ -> 
  nextActiveCubes(acc)
}

println(finalCubes.size)
