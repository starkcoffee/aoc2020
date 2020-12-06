import java.io.File

data class Group(val questions: List<Set<Char>>)

fun toGroup(multilineStr: String): Group {
  val lines = multilineStr.trim().split('\n')
  val answersPerPerson = lines.map{ it.filter{ it in 'a'..'z' }.toSet() }

  return Group(answersPerPerson)
}

fun Group.questionCount(): Int {
  // part 1
  // return this.questions.reduce(Set<Char>::union).size

  return this.questions.reduce(Set<Char>::intersect).size
}


val filename = "input"
val text = File(filename).readText()

val groupCounts = text.split("\n\n")            
  .map{ toGroup(it) }
  .map{ it.questionCount() }
 
println(groupCounts.sum())

