import java.io.File

val requiredFields = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

fun isNumberWithinRange(value: String, range: ClosedRange<Int>): Boolean {
  val number = value.toIntOrNull() 
  return when (number) {
    null -> false
    else -> number in range
  }
}

fun isValidField(fieldKey: String, value:String): Boolean {
  return when (fieldKey) {
    "byr" -> isNumberWithinRange(value, 1920..2002)
    "iyr" -> isNumberWithinRange(value, 2010..2020)
    "eyr" -> isNumberWithinRange(value, 2020..2030)
    "hgt" -> when (value.takeLast(2)) {
      "cm" -> isNumberWithinRange(value.dropLast(2), 150..193)
      "in" -> isNumberWithinRange(value.dropLast(2), 59..76)
      else -> false
    }
    "hcl" -> Regex("#[0-9a-f]{6}") matches value  // very tempted to parse as hex >.<
    "ecl" -> value in setOf("amb" ,"blu" ,"brn" ,"gry" ,"grn" ,"hzl" ,"oth")
    "pid" -> Regex("[0-9]{9}") matches value
    else -> true // ignore fields we don't care about
  } 
}

fun isValid(passport: Map<String,String>): Boolean {
  return passport.keys.containsAll(requiredFields) and passport.all{ (key,value) -> isValidField(key, value)}
}

val filename = "input-example-2"
val text = File(filename).readText()

val passportAsMaps = text.split("\n\n")         // [passport-string]
  .map{ it.trim() }
  .map{ it.split(" ","\n").map{ it.trim()} }    // [[field-value-string]]
  .map{ it.map{ it.split(':')} }                // [[[field,value]]]
  .map{ it.map{ it[0] to it[1] } }              // [[(field,value)]]
  .map{ it.toMap() }                            // [{field:value}]

println(passportAsMaps.count{ isValid(it) })

