package de.take_weiland.adventofcode2017.ms14

import de.take_weiland.adventofcode2017.ms10.getKnotHash
import de.take_weiland.adventofcode2017.ms12.removeFirst
import java.util.*
import kotlin.coroutines.experimental.buildSequence

fun main(args: Array<String>) {
    val input = "jzgqcdpd"
//    val input = "flqrgnkx"
    val rowHashes = (0..127).map { row -> getKnotHash("$input-$row") }
    val squares = rowHashes.map { row -> row.map { Integer.bitCount(it) }.sum() }.sum()
    println("Set squares: $squares")

    val groupAssignments = getGroups(getSquares(rowHashes))
    println(groupAssignments.size)
}

fun getSquares(rowHashes: List<List<Int>>): SortedSet<Pair<Int, Int>> {
    val result = sortedSetOf<Pair<Int, Int>>(compareBy({ it.first }, { it.second }))

    for (row in rowHashes.indices) {
        val cols = rowHashes[row]
        for (col in cols.indices) {
            result += cols[col].getSetIndices().map { Pair(row, it + col * 8) }
        }
    }

    return result
}

fun getGroups(squares: Set<Pair<Int, Int>>): Map<Int, List<Pair<Int, Int>>> {
    val todo = squares.toMutableSet()
    val groupAssignments = mutableListOf<Pair<Pair<Int, Int>, Int>>()
    var currentGroupId = 0
    while (todo.isNotEmpty()) {
        val square = todo.removeFirst()
        val groupElements = floodFillGroup(squares, square)
        val groupId = currentGroupId++
        groupElements.mapTo(groupAssignments) { Pair(it, groupId) }
        todo -= groupElements
    }
    return groupAssignments.groupBy({ it.second }, { it.first })
}

fun floodFillGroup(squares: Set<Pair<Int, Int>>, start: Pair<Int, Int>): Set<Pair<Int, Int>> {
    val todo = mutableSetOf(start)
    val result = mutableSetOf<Pair<Int, Int>>()
    while (todo.isNotEmpty()) {
        val square = todo.removeFirst()
        if (result.add(square)) {
            square.getNeighbors().filterTo(todo) { it in squares }
        }
    }
    return result
}

fun Pair<Int, Int>.getNeighbors() = buildSequence {
    yield(Pair(first - 1, second))
    yield(Pair(first + 1, second))
    yield(Pair(first, second - 1))
    yield(Pair(first, second + 1))
}

//
//fun getGroupCount(rowHashes: List<List<Int>>): Int {
//    val todo = rowHashes.mapIndexed { row, rowData ->
//        rowData.mapIndexedNotNull { col, colData ->  }
//    }
//    val groupAssignments = mutableMapOf<Pair<Int, Int>, Int>()
//    for (x in 0..127) {
//        for (y in 0..127) {
//            val around = listOf(Pair(x - 1, y), Pair(x, y - 1))
//            around.mapNotNull { groupAssignments[it] }
//        }
//    }
//}

fun Int.getSetIndices(): Set<Int> {
    return (0..31).filter { index -> isBitSet(index) }.mapTo(hashSetOf()) { 7 - it }
}

private fun Int.isBitSet(bit: Int) = this and (1 shl bit) != 0