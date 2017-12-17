package de.take_weiland.adventofcode2017.ms17

import kotlin.coroutines.experimental.buildSequence

fun main(args: Array<String>) {
    val x = generateInsertPositions(301)
            .withIndex()
            .take(50000000)
            .filter { (_, value) -> value == 1 }
            .last()
    println(x)
}

fun generateInsertPositions(stepSize: Int) = buildSequence {
    var current = 0
    var n = 0
    do {
        yield(current)

        n++
        current = (current + stepSize).rem(n) + 1
    } while (true)
}