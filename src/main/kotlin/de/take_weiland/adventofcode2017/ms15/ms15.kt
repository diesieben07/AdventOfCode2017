package de.take_weiland.adventofcode2017.ms15

import kotlin.coroutines.experimental.buildSequence

private const val MASK = (1L shl 16) -1

fun main(args: Array<String>) {
    val a = getGeneratorSequence(289, 16807)
    val b = getGeneratorSequence(629, 48271)

    println(getMatchingCount(a, b, 40_000_000))

    val aFilter = a.filter { it.rem(4) == 0L }
    val bFilter = b.filter { it.rem(8) == 0L }
    println(getMatchingCount(aFilter, bFilter, 5_000_000))
}

fun getMatchingCount(a: Sequence<Long>, b: Sequence<Long>, take: Int): Int {
    return a.zip(b).take(take).count { (a, b) -> (a and MASK) == (b and MASK) }
}

fun getGeneratorSequence(start: Long, factor: Long): Sequence<Long> {
    return buildSequence {
        var current = start
        do {
            current = (current * factor).rem(2147483647)
            yield(current)
        } while(true)
    }
}