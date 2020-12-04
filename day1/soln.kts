import java.io.File

val filename = "input"
val lines = File(filename).readLines()
val numbers = lines.map{ it.toInt() }.sorted()

var lower = 0
var higher = numbers.count() - 1

do {
  val sum = numbers[lower] + numbers[higher]

  if (sum == 2020) {
    println(numbers[lower] * numbers[higher])
    break
  }

  if (sum < 2020) lower++ else higher-- 
}
while (lower < higher)
