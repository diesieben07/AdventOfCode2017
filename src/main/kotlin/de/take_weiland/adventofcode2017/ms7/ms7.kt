package de.take_weiland.adventofcode2017.ms7

import com.google.common.io.Resources

/**
 * @author Take Weiland
 */
private val test = "pbga (66)\n" +
        "xhth (57)\n" +
        "ebii (61)\n" +
        "havc (66)\n" +
        "ktlj (57)\n" +
        "fwft (72) -> ktlj, cntj, xhth\n" +
        "qoyq (66)\n" +
        "padx (45) -> pbga, havc, qoyq\n" +
        "tknk (41) -> ugml, padx, fwft\n" +
        "jptl (61)\n" +
        "ugml (68) -> gyxo, ebii, jptl\n" +
        "gyxo (61)\n" +
        "cntj (57)"

fun main(args: Array<String>) {
    val input = parseInput(Resources.getResource("de/take_weiland/adventofcode2017/ms7/input.txt").readText())
//    val input = parseInput(test)
    val childrenOnly = input.mapValues { it.value.second }

    val allThatAreChildren = input.keys.flatMap { getChildrenRecursive(it, childrenOnly) }
    println(input.keys - allThatAreChildren)

    for ((key, value) in input) {
        val childrenWeights = value.second.map { it to getRecursiveWeight(it, input) }.toMap()
        if (childrenWeights.values.distinct().size > 1) {
            println("$key is unbalanced, children: " + childrenWeights + ", self weight ${input[key]?.first}")
        }
    }
}

fun getRecursiveWeight(root: String, input: Map<String, Pair<Int, List<String>>>): Int {
    return (getChildrenRecursive(root, input.mapValues { it.value.second }) + root)
            .map { element -> input[element]!!.first }
            .sum()
}

fun getChildrenRecursive(root: String, input: Map<String, List<String>>): List<String> {
    val immediateChildren = input[root] ?: throw Exception("Unknown root $root")
    val recursiveChildren = immediateChildren.flatMap { child -> getChildrenRecursive(child, input) }
    return immediateChildren + recursiveChildren
}

fun parseInput(input: String): Map<String, Pair<Int, List<String>>> {
    val lineRegex = Regex("^([a-z]+)\\s+\\(([0-9]+)\\)(?:\\s+->\\s+(.*))?\$", RegexOption.MULTILINE)
    return lineRegex.findAll(input)
            .map { match ->
                val name = match.groupValues[1]
                val weight = match.groupValues[2].toInt()
                val children = match.groupValues[3]
                        .takeIf { it.isNotBlank() }
                        ?.split(", ") ?: emptyList()
                Pair(name, Pair(weight, children))
            }
            .toMap()
}