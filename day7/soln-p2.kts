import java.io.File

val parentRegex = Regex("""\w+ \w+""")
val childRegex = Regex("""(\d+) (\w+ \w+)""")

data class Child(val bag: String, val num: Int)

fun parseParentAndChildren(line: String): Pair<String, List<Child>> {
  val parent = parentRegex.find(line)!!.value
  val children = childRegex.findAll(line).map{ it.destructured }.map{(num, bag) -> Child(bag, num.toInt()) }.toList()

  return Pair(parent, children)
}

fun getParents(bag: String, childToParentsGraph: Map<String, List<String>>): List<String> {
  val parents = childToParentsGraph.getOrDefault(bag, listOf())

  return parents + parents.map{ getParents(it, childToParentsGraph) }.flatten()
}

fun countChildBags(bag: String, parentToChildGraph: Map<String, List<Child>>): Int {
  val children = parentToChildGraph.getOrDefault(bag, listOf())

  return children.map{ it.num }.sum() + children.map{ it.num * countChildBags(it.bag, parentToChildGraph) }.sum() 
}

val filename = "input"
val lines = File(filename).readLines()

val parentToChildGraph = lines.map{ parseParentAndChildren(it) }.toMap()

val childToParentsGraph = parentToChildGraph
  .map{ (parent, children) -> children.map{ Pair(it.bag, parent) }}
  .flatten()
  .groupBy(keySelector = { it.first }, valueTransform = { it.second })

//println(getParents("shiny gold", childToParentsGraph).toSet().size)

println(countChildBags("shiny gold", parentToChildGraph))

