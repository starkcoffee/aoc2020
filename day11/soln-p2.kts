import java.io.File

class Grid(lines: List<List<Char>>) {

  val numCols = lines.first().size
  val numRows = lines.size
  var grid = lines.map{ line -> line.map{ if (it != '.') '?' else '.' }}

  fun getNeighbours(col: Int, row: Int): List<Char> {
    val neighbourCoords = (-1..1).flatMap{ j -> (-1..1).map{ k -> Pair(col+j, row+k)} }
      .filter{ it != Pair(col, row) }

    return neighbourCoords.map{ (j, k) -> getVisibleNeighbour(j, k, col, row) }
      .filterNotNull()
  }

  fun getVisibleNeighbour(j: Int, k: Int, fromCol: Int, fromRow: Int): Char? {
    val neighbour = get(j, k)
    if (neighbour != '.') return neighbour

    return getVisibleNeighbour(j + j-fromCol, k + k-fromRow, j, k)
  }

  fun get(col: Int, row: Int): Char? {
    if (col in (0..numCols-1) && row in (0..numRows-1)){
      return grid[row][col]
    } else {
      return null
    }
  }

  fun setSeatsWithLessThanFiveUnsetNeighboursToOccupied(): Grid {
    mapSeats{ col, row, seat ->
      when {
        seat == '?' && getNeighbours(col, row).filter{ it == '?'}.size < 5 -> '#'
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
    .setSeatsWithLessThanFiveUnsetNeighboursToOccupied()
    .setSeatsNextToOccupiedToEmpty()
  iterations++
}

println("iterations: ${iterations}")
println(grid.numSeats())


