import java.io.File

val filename = "input"
val text = File(filename).readText()

val groups = text.split("\n\n")                 // [group-multline]
  .map{ it.filter{ it in 'a'..'z'}.toSet() }    // [set(chars)]
 
println(groups.map{ it.size }.sum())

