import java.io.File


val filename = "input"
val line = File(filename).readLines()[0]
println(line)

val numbers = line.split(',').map{ it.toInt() }

val array = mutableListOf<Int>()
(0..30000000-1).forEach{ array.add(-1) }

numbers.withIndex().forEach{ (i, n) ->
  array[n] = i
}

var count = 0
val last =(numbers.size+1..30000000-1).asSequence().fold(0){ prev, i ->
  count++
  if (count % 1000 == 0) println("${count}")
  val next = when {
    array[prev] == -1 -> 0
    else -> i-1 - array[prev]
  }
  array[prev] = i-1
  next
}

println(last)
