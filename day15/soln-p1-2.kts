import java.io.File


val filename = "input"
val line = File(filename).readLines()[0]
println(line)

val numbers = line.split(',').map{ it.toInt() }

val nmap: MutableMap<Int, Int> = numbers.withIndex().fold(mutableMapOf()){ acc, n_i ->
  acc[n_i.value] = n_i.index
  acc
}

var count = 0
val last =(numbers.size+1..30000000-1).asSequence().fold(0){ prev, i ->
  count++
  if (count % 1000 == 0) println("${count}, ${nmap.size}")
  val next = when {
    !nmap.containsKey(prev) -> 0
    else -> i-1 - nmap[prev]!!
  }
  nmap[prev] = i-1
  next
}

println(last)
