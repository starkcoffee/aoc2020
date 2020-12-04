import java.io.File


fun findPairWithSum(targetSum: Int, list: List<Int>): Pair<Int, Int>? {
  var lower = 0
  var higher = list.count() - 1

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

val filename = "input-example"
val lines = File(filename).readLines()
val numbers = lines.map{ it.toInt() }.sorted()

val pair = findPairWithSum(2020, numbers)

if (pair != null) {
  println(pair.first * pair.second)
} else {
  println("Not found")
}
