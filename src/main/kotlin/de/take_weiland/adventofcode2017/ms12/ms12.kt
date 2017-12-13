package de.take_weiland.adventofcode2017.ms12

import de.take_weiland.adventofcode2017.getInput

/**
 * @author Take Weiland
 */
fun main(args: Array<String>) {
//    val input = parseInput("0 <-> 2\n" +
//            "1 <-> 1\n" +
//            "2 <-> 0, 3, 4\n" +
//            "3 <-> 2, 4\n" +
//            "4 <-> 2, 3, 6\n" +
//            "5 <-> 6\n" +
//            "6 <-> 4, 5")

    val input = parseInput(getInput(12))

    val connections = getInitialConnections(input)
    val recursiveConnections = connections.mapValues { getRecursiveConnections(it.key, connections) }
    println(recursiveConnections["0"]!!.size)

    println(getGroups(recursiveConnections).size)
}

fun <T> MutableIterable<T>.removeFirst(): T {
    with (iterator()) {
        val e = next()
        remove()
        return e
    }
}

fun getGroups(recursiveConnections: Map<String, Set<String>>): List<Set<String>> {
    val groups = mutableListOf<MutableSet<String>>()
    val mutableConnections = recursiveConnections.toMutableMap()
    while (mutableConnections.isNotEmpty()) {
        val (_, connections) = mutableConnections.entries.removeFirst()

        val group = groups.find { g -> connections.any { c -> c in g } } ?: mutableSetOf<String>().also { groups += it }
        group += connections
    }
    return groups
}

fun parseInput(input: String): List<Pair<String, List<String>>> {
    return Regex("^(.+?) <-> (.+?)$", RegexOption.MULTILINE)
            .findAll(input)
            .map {
                Pair(it.groupValues[1], it.groupValues[2].split(",").map { it.trim() })
            }
            .toList()
}

fun getInitialConnections(raw: List<Pair<String, List<String>>>): Map<String, Set<String>> {
    fun expandRawConnections(program: String, rawConnections: List<String>): List<Pair<String, String>> {
        val rawPairs = rawConnections.map { Pair(program, it) }
        val reversed = rawConnections.map { Pair(it, program) }
        val self = Pair(program, program)
        return rawPairs + reversed + self
    }

    return raw
            .flatMap { (program, rawConnections) ->
                expandRawConnections(program, rawConnections)
            }
            .groupBy { it.first }
            .mapValues { it.value.map { it.second }.toSet() }
}

fun getRecursiveConnections(program: String, initialConnections: Map<String, Set<String>>): Set<String> {
    val result = mutableSetOf<String>()
    val done = mutableSetOf<String>()
    val todo = mutableListOf<String>()

    fun getConnections(program: String) = initialConnections[program] ?: throw Exception("Unknown program $program")

    todo += getConnections(program)

    while (todo.isNotEmpty()) {
        val next = todo.removeAt(todo.lastIndex)
        if (next !in done) {
            done += next
            result += next
            todo += getConnections(next)
        }
    }
    return result
}