import java.io.File


val filename = "input"
val line = File(filename).readLines()[0]
println(line)

val numbers = line.split(',').map{ it.toInt() }

val startMap: Map<Int, Int> = numbers.withIndex().fold(mapOf()){ acc, n_i ->
  acc + mapOf(n_i.value to n_i.index)
}

val numMapAndPrev =(numbers.size+1..100000-1).fold(Pair(startMap, 0)){ (nmap, prev), i ->
  when {
    !nmap.containsKey(prev) -> Pair(nmap + mapOf(prev to i-1), 0)
    else -> Pair(nmap + mapOf(prev to i-1), i-1 - nmap[prev]!!)
  }
}

println()
println(numMapAndPrev.second)
