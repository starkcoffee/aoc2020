import java.io.File

data class Policy(val positions: Set<Int>, val letter: Char) 

data class PasswordEntry(val policy: Policy, val password: String) {

  fun isValid(): Boolean {
    val allowedIndices = this.policy.positions.map { it - 1 }
    return this.password.withIndex().count { (index, letter) -> letter == this.policy.letter && index in allowedIndices } == 1
  }
}

val filename = "input"
val lines = File(filename).readLines()

val regex = Regex("""(\d+)-(\d+) (\w): (\w+)""")

val passwordEntries = lines.map { line -> 
  val (pos1Str, pos2Str, letter, password) = regex.matchEntire(line)!!.destructured
  PasswordEntry(Policy(setOf(pos1Str.toInt(), pos2Str.toInt()), letter.single()), password)
}

val numValid = passwordEntries.count{ it.isValid() }
println(numValid)




