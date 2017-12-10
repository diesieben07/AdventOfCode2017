package de.take_weiland.adventofcode2017.ms3

import java.util.*
import kotlin.coroutines.experimental.buildSequence
import kotlin.math.absoluteValue

/**
 * @author Take Weiland
 */
enum class Direction(val dx: Int, val dy: Int) {

    R(1, 0),
    U(0, 1),
    L(-1, 0),
    D(0, -1)

}

fun coords(): Sequence<Pair<Int, Int>> {
    return buildSequence {
        var x = 0
        var y = 0
        for (dir in directions()) {
            yield(Pair(x, y))
            x += dir.dx
            y += dir.dy
        }
    }
}

fun directions(): Sequence<Direction> {
    return loopingDirections()
            .chunked(2)
            .zip(directionCounts()) { dirChunk, count -> dirChunk.flatMap { dir -> Collections.nCopies(count, dir) } }
            .flatMap { it.asSequence() }
}

fun directionCounts(): Sequence<Int> {
    return generateSequence(1) { it + 1 }
}

fun loopingDirections(): Sequence<Direction> {
    return buildSequence {
        while (true) {
            yieldAll(Direction.values().iterator())
        }
    }
}

fun main(args: Array<String>) {
    println(getCoords(347991).getDistToZero())
    println(directions().take(20).toList())
}

fun Pair<Int, Int>.getDistToZero() = first.absoluteValue + second.absoluteValue

fun getCoords(n: Int): Pair<Int, Int> {
    return coords().elementAt(n - 1)
}