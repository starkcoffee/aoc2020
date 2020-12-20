import java.io.File


val filename = "input"
val file = File(filename).readText()

val sections = file.split("\n\n")

val rangesRegex = Regex("""(\d+)-(\d+)""")
val ranges = sections[0].split("\n").map{ line ->
  rangesRegex.findAll(line)
    .map{ it.groupValues.drop(1).map{ it.toInt()} }
    .map{ (a, b) -> a..b }
    .toList()
}.flatten()

val validNumbers: Set<Int> = ranges.fold(setOf()){ acc, r -> acc + r.toSet() }

val nearbyTickets = sections[2].trim().split("\n").drop(1)
  .map{ it.split(",").map{ it.toInt()} }

val invalidNumbers = nearbyTickets.map{ it.filter{ n -> !validNumbers.contains(n) } }


println(invalidNumbers.flatten().sum())
