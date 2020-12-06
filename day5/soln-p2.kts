import java.io.File

fun toId(pass: String): Int {
  val binaryStr = pass.replace('F','0').replace('B','1').replace('L','0').replace('R','1')  
  return binaryStr.toInt(2)
}

val filename = "input"
val lines = File(filename).readLines()

val seatIds = lines.map{ toId(it) }

//println(seatIds.maxOrNull())

val firstSeatId = seatIds.minOrNull() ?: 0

val n = seatIds.size
val expectedSum = (n+1)*(firstSeatId-1) + (n+1)*(n+2)/2 
val actualSum = seatIds.sum()
val mySeatId = expectedSum - actualSum

println(mySeatId)


