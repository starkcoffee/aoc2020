import java.io.File

val filename = "input"
val lines = File(filename).readLines()

val arrivalTime = lines[0].toInt()
val buses = lines[1].split(',').filter{ it != "x" }.map{ it.toInt() }

val minsToWait = buses.map{ busId -> Pair(busId, busId - arrivalTime % busId) }
val min = minsToWait.minByOrNull{ it.second }
println(min!!.first * min.second)

