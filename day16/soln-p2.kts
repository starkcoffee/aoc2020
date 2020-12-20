import java.io.File
import java.math.BigInteger 

val filename = "input"
val file = File(filename).readText()

val sections = file.split("\n\n")

val rangesRegex = Regex("""(\d+)-(\d+)""")
val ranges = sections[0].split("\n").map{ line ->
  rangesRegex.findAll(line)
    .map{ it.groupValues.drop(1).map{ it.toInt()} }
    .map{ (a, b) -> a..b }
    .toList()
}.map{ it[0].toSet() + it[1].toSet() }


val validNumbers: Set<Int> = ranges.fold(setOf()){ acc, r -> acc + r }

val nearbyTickets = sections[2].trim().split("\n").drop(1)
  .map{ it.split(",").map{ it.toInt()} }

fun containsInvalidNumbers(ticket: List<Int>): Boolean {
  return ticket.any{ n -> !validNumbers.contains(n) }
}

val validTickets = nearbyTickets.filter{ !containsInvalidNumbers(it) }

// zip ticket fields together
val fieldValues = (0..validTickets[0].size-1).map{ i -> validTickets.map{ it[i] } }

val fieldCandidates = fieldValues.map { values -> 
  ranges.withIndex().filter{ (_, range) -> (values - range).isEmpty() }.map{ it.index }
}
.withIndex().map{(fieldIndex, candidates) -> fieldIndex to candidates }

fun reduce(fieldCandidates: List<Pair<Int, List<Int>>>): List<Pair<Int, List<Int>>> {
  val (singles, rest) = fieldCandidates.partition{ it.second.size == 1 } 
  if (rest.isEmpty()) return singles

  val singleCandidates = singles.map{ it.second }.flatten()
  return reduce(singles + rest.map{ Pair(it.first, it.second - singleCandidates) })
}

// map of index of field as it is declared to where it appears in tickets
val fieldMappings = reduce(fieldCandidates).map{ Pair(it.second[0], it.first) }.toMap()

val departureFields = (0..5)
val myTicket = sections[1].split("\n").drop(1)[0].split(',').map{ it.toBigInteger() }

println(departureFields.map{ i -> myTicket[fieldMappings[i]!!]}.reduce(BigInteger::times))
