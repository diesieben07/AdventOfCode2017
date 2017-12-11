package de.take_weiland.adventofcode2017.ms10

import de.take_weiland.adventofcode2017.getInput
import java.util.*

/**
 * @author Take Weiland
 */
fun main(args: Array<String>) {
    val input = getInput(10)

    val initialState = State((0..255).toList(), 0, 0)
    val lengths = input.split(',').map { it.toInt() }
//    val initialState = State(listOf(0, 1, 2, 3, 4), 0, 0)
//    val lengths = listOf(3, 4, 1, 5)
    val lastState = getLastState(lengths, initialState)
    println(lastState)
    println(lastState.list.take(2).reduce(Int::times))
}

fun getLastState(lengths: Iterable<Int>, initialState: State): State {
    return lengths.fold(initialState, State::next)
}

fun State.next(length: Int): State {
    val newList = list.toMutableList()
    newList.reverseSublistWrap(pos, pos + length)
    return State(newList, (pos + skipSize + length).rem(newList.size), skipSize + 1)
}

fun <T> MutableList<T>.reverseSublistWrap(from: Int, to: Int) {
    val midPoint = from + (to - from) / 2
    for (i in from until midPoint) {
        val j = to - 1 - (i - from)
        Collections.swap(this, i.rem(size), j.rem(size))
    }
}

data class State(val list: List<Int>, val pos: Int, val skipSize: Int)