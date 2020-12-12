import java.io.File

val filename = "input"
val lines = File(filename).readLines()

val numbers = lines.map{ it.toInt() }

val startingAdapter = 0
val device = (numbers.maxOrNull() ?: 0) + 3
val joltages = (listOf(startingAdapter) + numbers + listOf(device)).sorted()

val joltDiffs = joltages.zipWithNext().map{ it.second - it.first } 
val joltDiffHistogram = joltDiffs.groupingBy{ it }.eachCount() 

println(joltDiffHistogram)
println(joltDiffHistogram[1]!! * joltDiffHistogram[3]!!)

