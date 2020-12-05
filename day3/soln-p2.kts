import java.io.File
import java.math.BigInteger


fun treeCount(lines: List<String>, step_across:Int, step_down:Int): Int {
  val num_rows = lines.size
  val num_cols = lines.first().length

  val coords = (step_down..num_rows-1 step step_down).map{ row -> Pair(row/step_down*step_across, row) }

  val path = coords.map{(col,row) -> lines[row][col%num_cols]}
  val numTrees = path.count{ it == '#' }

  return numTrees
}

val filename = "input"
val lines = File(filename).readLines()

val slopes = listOf(Pair(1,1), Pair(3,1), Pair(5,1), Pair(7,1), Pair(1,2))
val trees_per_path = slopes.map{ (step_across, step_down) -> treeCount(lines, step_across, step_down) }
println(trees_per_path)
val result = trees_per_path.map{it.toBigInteger()}.reduce(BigInteger::times)
println(result)
