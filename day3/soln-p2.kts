import java.io.File
import java.math.BigInteger


fun treeCount(lines: List<String>, step_across:Int, step_down:Int): Int {
  val num_rows = lines.size
  val num_cols = lines.first().length

  var i = step_across
  var tree_count = 0

  for (j in step_down..num_rows-1 step step_down){
    if (lines[j][i%num_cols] == '#') {
      tree_count+= 1
    }
    i+= step_across
  }

  return tree_count
}

val filename = "input"
val lines = File(filename).readLines()

val slopes = listOf(Pair(1,1), Pair(3,1), Pair(5,1), Pair(7,1), Pair(1,2))
val trees_per_path = slopes.map{ (step_across, step_down) -> treeCount(lines, step_across, step_down) }
println(trees_per_path)
val result = trees_per_path.map{it.toBigInteger()}.reduce(BigInteger::times)
println(result)
