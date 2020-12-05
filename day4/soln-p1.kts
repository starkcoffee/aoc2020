import java.io.File

val requiredFields = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid") 

fun isValid(passport: Map<String,String>): Boolean {
  return passport.keys.containsAll(requiredFields)
}

val filename = "input"
val text = File(filename).readText()

val passportAsMaps = text.split("\n\n")         // [passport-string]
  .map{ it.trim() }
  .map{ it.split(" ","\n").map{ it.trim()} }    // [[field-value-string]]
  .map{ it.map{ it.split(':')} }                // [[[field,value]]]
  .map{ it.map{ it[0] to it[1] } }              // [[(field,value)]]
  .map{ it.toMap() }                            // [{field:value}]

println(passportAsMaps.count{ isValid(it) })
