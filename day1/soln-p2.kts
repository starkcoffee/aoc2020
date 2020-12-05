import java.io.File


fun findPairWithSum(targetSum: Int, list: List<Int>): Pair<Int, Int>? {

  var lower = 0
  var higher = list.count() - 1

  if (list.isEmpty()) return null

  do {
    val sum = list[lower] + list[higher]

    if (sum == targetSum) {
      return Pair(list[lower], list[higher])
    }

    if (sum < targetSum) lower++ else higher-- 
  }
  while (lower < higher)

  return null
}

fun findTripleWithSum(targetSum: Int, list: List<Int>): Triple<Int, Int, Int>? {

  for ((index, number) in list.withIndex()) {
    val pair = findPairWithSum(targetSum - number, list.slice(index+1..list.size-1))
    if (pair != null) {
      return Triple(number, pair.first, pair.second) 
    }
  }

  return null
}

val filename = "input"
val lines = File(filename).readLines()
val numbers = lines.map{ it.toInt() }.sorted()

val triple = findTripleWithSum(2020, numbers)

if (triple != null) {
  println(triple.toList().reduce(Int::times))
} else {
  println("Not found")
}
