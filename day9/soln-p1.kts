import java.io.File
import java.math.BigInteger

val PREAMBLE_SIZE = 25 

// use @ohookins's algorithm from day1-part1
fun containsPairWithSum(targetSum: BigInteger, numbers: Set<BigInteger>): Boolean {
  for (num in numbers) {
    if ((targetSum - num) in numbers ) {
      return true
    }
  }
  return false
}


val filename = "input"
val lines = File(filename).readLines()

val numbers = lines.map{ it.toBigInteger() }

val numberToFind = (PREAMBLE_SIZE..numbers.size-1)
  .filter{ i -> !containsPairWithSum(numbers[i], numbers.slice(i-PREAMBLE_SIZE..i-1).toSet()) }
  .map{ numbers[it] }
  .first()

println(numberToFind)
