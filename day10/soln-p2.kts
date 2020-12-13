import java.io.File
import java.math.BigInteger as BigInt

fun <T> List<T>.replaceLastWith(item: T): List<T> = this.dropLast(1) + listOf(item)

val filename = "input"
val lines = File(filename).readLines()

val numbers = lines.map{ it.toInt() }

val startingAdapter = 0
val device = (numbers.maxOrNull() ?: 0) + 3
val joltages = (listOf(startingAdapter) + numbers + listOf(device)).sorted()

fun generateEdges(fromVoltage: Int, joltages: List<Int>): List<Pair<Int,List<Int>>> {
  if (joltages.isEmpty()) return listOf()

  return listOf(Pair(fromVoltage, joltages.take(3).filter{ it - fromVoltage <= 3 })) + generateEdges(joltages.first(), joltages.drop(1))
}

val edges = generateEdges(0, joltages.drop(1))
println(edges)

fun countPaths(currentHeadsFreqMap:Map<Int,BigInt>, edges: Iterator<Pair<Int,List<Int>>>): BigInt {
  if (!edges.hasNext()) return currentHeadsFreqMap.values.reduce{acc, n -> n + acc}

  val nextEdges = edges.next()

  val headsFreqMap = currentHeadsFreqMap.map{ (k,v) -> 
      if (k == nextEdges.first){ 
        nextEdges.second.map{ it to v } 
      }
      else {
        listOf(k to v) 
      }
    } 
    .flatten()
    .groupingBy{ it.first }.fold(0.toBigInteger()){acc, pair -> acc+pair.second}.toMap()

  return countPaths(headsFreqMap, edges)
}

println(countPaths(mapOf(0 to 1.toBigInteger()), edges.iterator()))


