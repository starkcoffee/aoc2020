import java.io.File
import java.math.BigInteger as BigInt

val PREAMBLE_SIZE = 25 

// a functional version of @ohookins's algorithm from day1-part1
fun containsPairWithSum(targetSum: BigInt, numbers: Set<BigInt>): Boolean {
  val complements = numbers.map { targetSum - it }
  return (numbers + complements).size < numbers.size*2
}

val filename = "input"
val lines = File(filename).readLines()

val numbers = lines.map{ it.toBigInteger() }

val keystoneNumber = (PREAMBLE_SIZE..numbers.size-1)
  .filter{ i -> !containsPairWithSum(numbers[i], numbers.slice(i-PREAMBLE_SIZE..i-1).toSet()) }
  .map{ numbers[it] }
  .first()

println(keystoneNumber)

fun sum(bigInts: List<BigInt>): BigInt {
  return bigInts.fold(0.toBigInteger()){ acc, n -> acc+n} 
}

fun findStreak(streakSum: BigInt, streak: List<BigInt>, targetSum: BigInt, iterator: Iterator<BigInt>): List<BigInt> {
  if (!iterator.hasNext()) return listOf()

  if (streakSum == targetSum) return streak
  if (streakSum > targetSum) return findStreak(streakSum-streak.first(), streak.drop(1), targetSum, iterator)

  val nextNum = iterator.next()
  return findStreak(streakSum+nextNum, streak + listOf(nextNum), targetSum, iterator)
}

val streak = findStreak(0.toBigInteger(), listOf(), keystoneNumber, numbers.iterator())
println(streak.minOrNull()!! + streak.maxOrNull()!!)
