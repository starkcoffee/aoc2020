import java.io.File

val parentRegex = Regex("""\w+ \w+""")
val childRegex = Regex("""\d+ (\w+ \w+)""")

fun parseParentAndChildren(line: String): Pair<String, List<String>> {
  val parent = parentRegex.find(line)!!.value
  val children = childRegex.findAll(line).map{ it.groupValues[1] }.toList()

  return Pair(parent, children)
}

fun getParents(bag: String, childToParentsGraph: Map<String, List<String>>): List<String> {
  val parents = childToParentsGraph.getOrDefault(bag, listOf())

  return parents + parents.map{ getParents(it, childToParentsGraph) }.flatten()
}

val filename = "input"
val lines = File(filename).readLines()

val parentToChildGraph = lines.map{ parseParentAndChildren(it) }.toMap()

val childToParentsGraph = parentToChildGraph
  .map{ (parent, children) -> children.map{ Pair(it, parent) }}
  .flatten()
  .groupBy(keySelector = { it.first }, valueTransform = { it.second })

println(getParents("shiny gold", childToParentsGraph).toSet().size)
