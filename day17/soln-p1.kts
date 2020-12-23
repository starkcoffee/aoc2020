import java.io.File

typealias Coord = Triple<Int,Int,Int>

val filename = "input"
val lines = File(filename).readLines()

val cubes = lines.withIndex().flatMap{ (x, line) 
  -> line.withIndex().map{ (y, c) -> Pair(Triple(x,y,0), c) }}.toMap()

fun getNeighbours(cubes: Map<Coord, Char>, coord: Coord): List<Pair<Coord, Char>> {
  val neighbourCoords = (-1..1).flatMap{ z -> 
    (-1..1).flatMap{ y -> 
    (-1..1).map{ x -> Triple(coord.first+x, coord.second+y, coord.third+z) }}}
    .filter{ it != coord }

  return neighbourCoords.map{ ncoord -> Pair(ncoord, cubes.getOrDefault(ncoord, '.')) }
}

fun growByOneDimension(cubes: Map<Coord, Char>): Map<Coord, Char> {
  return cubes.entries.fold(mapOf()){ acc, (coord, c) ->
    val neighbours = getNeighbours(cubes, coord)
    val current = listOf(Pair(coord, c))

    acc + (neighbours + current).toMap()
  }
}

fun toString(cubes: Map<Coord, Char>): String { 
  return cubes.entries.sortedBy { it.key.third }.groupBy{ it.key.third }.values.map{
    it.sortedBy{ it.key.first }.groupBy{ it.key.first }.values.map{ 
      it.sortedBy{ it.key.second }.map{ it.value }.joinToString("")
    }.joinToString("\n")
  }.joinToString("\n\n")
}

fun nextCubes(cubes: Map<Coord, Char>): Map<Coord, Char> {
  val nextCubes = growByOneDimension(cubes) 
  return nextCubes.entries.map{ (coord, state) -> 
    val activeNeighbours = getNeighbours(cubes, coord).count{ it.second == '#' }
    val nextState = when (state) {
      '#' -> when (activeNeighbours in (2..3)) {
        true -> '#'
        false -> '.'
      }
      else -> when (activeNeighbours == 3) {
        true -> '#'
        else -> '.'
      }
    }
    Pair(coord, nextState)
  }.toMap()
}

val finalCubes = (1..6).fold(cubes){ acc, _ -> 
  nextCubes(acc)
}

//println(toString(finalCubes))

println(finalCubes.values.count{ it == '#' })
