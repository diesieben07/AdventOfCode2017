package de.take_weiland.adventofcode2017.ms3

/**
 * @author Take Weiland
 */
fun main(args: Array<String>) {
    println(getFirstValueHigherThan(347991))
}

fun getFirstValueHigherThan(input: Int): Int {
    val grid = mutableMapOf<Pair<Int, Int>, Int>()
    grid[Pair(0, 0)] = 1
    for ((x, y) in coords().drop(1)) {
        var v = 0
        for (dx in -1..1) {
            for (dy in -1..1) {
                grid[Pair(x + dx, y + dy)]?.let { v += it }
            }
        }
        if (v > input) {
            return v
        }
        grid[Pair(x, y)] = v
    }
    throw Exception()
}