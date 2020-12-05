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

val passwordEntries = lines.map { line -> 
  val (policyStr, password) = line.split(':').map{ it.trim() }
  val (rangeStr, letter) = policyStr.split(' ')
  val (from, to) = rangeStr.split('-').map{ it.toInt() }
  PasswordEntry(Policy(from..to, letter.single()), password)
}

val numValid = passwordEntries.count{ it.isValid() }
println(numValid)




