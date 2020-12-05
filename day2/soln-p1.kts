import java.io.File

data class Policy(val range: ClosedRange<Int>, val letter: Char)

data class PasswordEntry(val policy: Policy, val password: String) {

  fun isValid(): Boolean {
    val numTimesPolicyLetterOccurs = this.password.count{ it == this.policy.letter } 
    return numTimesPolicyLetterOccurs in this.policy.range
  }
}

val filename = "input"
val lines = File(filename).readLines()

val regex = Regex("""(\d+)-(\d+) (\w): (\w+)""")

val passwordEntries = lines.map { line -> 
  val (fromStr, toStr, letter, password) = regex.matchEntire(line)!!.destructured
  PasswordEntry(Policy(fromStr.toInt()..toStr.toInt(), letter.single()), password)
}

val numValid = passwordEntries.count{ it.isValid() }
println(numValid)




