import java.io.File

val filename = "input"
val lines = File(filename).readLines()
val num_rows = lines.size
val num_cols = lines.first().length

val total_dots_across = 1 + 3*(num_rows-1)
val repeats_needed = total_dots_across / num_cols

val forestMap = lines.map{ line ->
  line.repeat(1 + repeats_needed) // it counts the original string as a "repeat" 
}

val coords = (1..num_rows-1).map{ row -> Pair(row*3, row) }

val path = coords.map{(col,row) -> forestMap[row][col]}
val numTrees = path.count{ it == '#' }
println(numTrees)
