import java.io.File

class Grid(lines: List<List<Char>>) {

  val numCols = lines.first().size
  val numRows = lines.size
  var grid = lines.map{ line -> line.map{ if (it != '.') '?' else '.' }}

  fun getNeighbours(col: Int, row: Int): List<Char> {
    val coords = (-1..1).flatMap{ j -> (-1..1).map{ k -> Pair(col+j, row+k)} }
      .filter{ it != Pair(col, row) }

    return coords.map{ (col, row) -> this.get(col, row) }.filterNotNull().filter{ it != '.' }
  }

  fun get(col: Int, row: Int): Char? {
    if (col in (0..numCols-1) && row in (0..numRows-1)){
      return grid[row][col]
    } else {
      return null
    }
  }

  fun setSeatsWithLessThanFourUnsetNeighboursToOccupied(): Grid {
    mapSeats{ col, row, seat ->
      when {
        seat == '?' && getNeighbours(col, row).filter{ it == '?'}.size < 4 -> '#'
        else -> seat
      }
    }
    return this
  }

  fun setSeatsNextToOccupiedToEmpty(): Grid {
    mapSeats{ col, row, seat ->
      when {
        seat == '?' && getNeighbours(col, row).contains('#') -> 'L'
        else -> seat
      }
    }
    return this
  }

  fun numSeats(): Int {
    return grid.map{ it.count{ it == '#' }}.sum()
  }

  fun containsUnsetSeats(): Boolean {
    return grid.any{ it.contains('?') }
  }

  private fun mapSeats(transform: (Int, Int, Char) -> Char) {
    this.grid = this.grid.withIndex().map{ (row, line) ->
      line.withIndex().map{ (col, seat) -> 
        transform(col, row, seat)
      }
    }
  }
}

val filename = "input"
val lines = File(filename).readLines()

val grid = Grid(lines.map{ it.toList() })

var iterations = 0
while(grid.containsUnsetSeats()) {
  grid
    .setSeatsWithLessThanFourUnsetNeighboursToOccupied()
    .setSeatsNextToOccupiedToEmpty()
  iterations++
}

println("iterations: ${iterations}")
println(grid.numSeats())


