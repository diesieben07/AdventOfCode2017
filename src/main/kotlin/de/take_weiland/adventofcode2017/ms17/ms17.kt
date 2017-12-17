package de.take_weiland.adventofcode2017.ms17

fun main(args: Array<String>) {
    val stepSize = 301

    val initialState = State(listOf(0), 0)

    val lastState = (0..2017).fold(initialState) { state, _ -> state.next(stepSize) }
    println(lastState.list[lastState.list.indexOf(2017) + 1])
}

private data class State(val list: List<Int>, val pos: Int)

private fun State.next(stepSize: Int): State {
    val valueToAdd = list.size
    val newList = list.toMutableList()
    val newPos = (pos + stepSize).rem(list.size) + 1
    newList.add(newPos, valueToAdd)
    return State(newList, newPos)
}