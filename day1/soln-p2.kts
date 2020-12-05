import java.io.File


fun findPairWithSum(targetSum: Int, sortedList: List<Int>): Pair<Int, Int>? {

  if (sortedList.isEmpty()) return null

  var lower = 0
  var higher = sortedList.count() - 1

  do {
    val sum = sortedList[lower] + sortedList[higher]

    if (sum == targetSum) {
      return Pair(sortedList[lower], sortedList[higher])
    }

    if (sum < targetSum) lower++ else higher-- 
  }
  while (lower < higher)

  return null
}

fun findTripleWithSum(targetSum: Int, sortedList: List<Int>): Triple<Int, Int, Int>? {

  for ((index, number) in sortedList.withIndex()) {
    val pair = findPairWithSum(targetSum - number, sortedList.slice(index+1..sortedList.size-1))
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
