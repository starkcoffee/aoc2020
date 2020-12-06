import java.io.File

fun toId(pass: Pair<String,String>): Int {
  val binaryStr = pass.first.replace('F','0').replace('B','1') + pass.second.replace('L','0').replace('R','1')  
  return binaryStr.toInt(2)
}

val filename = "input"
val lines = File(filename).readLines()

val passes = lines.map{ Pair(it.slice(0..6), it.slice(7..9)) }

val rowComparator = compareBy<Pair<String,String>> { it.first }
val rowAndColComparator = rowComparator.thenByDescending { it.second }

val highest = passes.sortedWith(rowAndColComparator).first()

println(toId(highest))


