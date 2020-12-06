import java.io.File

fun toId(pass: String): Int {
  val binaryStr = pass.replace('F','0').replace('B','1').replace('L','0').replace('R','1')  
  return binaryStr.toInt(2)
}

val filename = "input"
val lines = File(filename).readLines()

val passes = lines.map{ toId(it) }

println(passes.maxOrNull())


