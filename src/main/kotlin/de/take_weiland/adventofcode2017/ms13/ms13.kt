package de.take_weiland.adventofcode2017.ms13

import de.take_weiland.adventofcode2017.getInput

/**
 * @author Take Weiland
 */
private val test = "0: 3\n" +
        "1: 2\n" +
        "4: 4\n" +
        "6: 4"

fun main(args: Array<String>) {
//    val input = parseInput(test)
    val input = parseInput(getInput(13))

    println(getTotalSeverity(0, input))
    val minDelay = (0..Integer.MAX_VALUE)
            .first { delay -> !isCaughtAnywhere(delay, input) }
    println(minDelay)
}

fun parseInput(input: String): Map<Int, Int> {
    return input.lineSequence()
            .map { it.split(":").map { it.trim() } }
            .map { (depth, range) -> Pair(depth.toInt(), range.toInt()) }
            .toMap()
}

fun isCaughtAnywhere(delay: Int, ranges: Map<Int, Int>): Boolean {
    return ranges.entries.any { (layer, range) -> getSeverityIfCaught(layer, delay, range) != null }
}

fun getTotalSeverity(delay: Int, ranges: Map<Int, Int>): Int {
    return ranges.entries.sumBy { (layer, range) -> getSeverityIfCaught(layer, delay, range) ?: 0 }
}

fun getSeverityIfCaught(layer: Int, delay: Int, range: Int): Int? {
    val ps = layer + delay // time when we arrive at this layer
    val scannerAtTop = isScannerAtTop(ps, range)
    return when {
        scannerAtTop -> layer * range
        else -> null
    }
}

fun isScannerAtTop(ps: Int, range: Int): Boolean {
    val steps = (range - 1) * 2
    val step = ps.rem(steps)
    return step == 0
}

fun printSimulation(layer: Int, delay: Int, ranges: Map<Int, Int>) {
    val maxLayer = ranges.keys.max()!!
    val maxRange = ranges.values.max()!!

    println((0..maxLayer).joinToString("   ", " ", " "))

    for (index in 0 until maxRange) {
        for (currentLayer in 0..maxLayer) {
            val iAmHere = index == 0 && (layer - delay) == currentLayer
            val (lBracket, rBracket) = if (iAmHere) {
                Pair("(", ")")
            } else {
                Pair("[", "]")
            }
            val range = ranges[currentLayer]
            when {
                range == null -> if (iAmHere) print("(.)") else print("...")
                range <= index -> print("   ")
                else -> {
                    print(lBracket)
                    val scannerPosition = getScannerPosition(layer + delay, range)
                    if (scannerPosition == index) {
                        print("S")
                    } else {
                        print(" ")
                    }
                    print(rBracket)
                }
            }

            print(" ")
        }
        println()
    }

    println()
}

fun getScannerPosition(ps: Int, range: Int): Int {
    val steps = (range - 1) * 2
    val step = ps.rem(steps)
    return if (step in 0 until range) {
        step
    } else {
        (2 * range) - 2 - step
    }
}
