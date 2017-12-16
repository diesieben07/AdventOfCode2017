package de.take_weiland.adventofcode2017.ms10

import de.take_weiland.adventofcode2017.getInput

/**
 * @author Take Weiland
 */
private val postfix = listOf(17, 31, 73, 47, 23)
private val rounds = 64

fun main(args: Array<String>) {
    val output = getKnotHash(getInput(10))
            .joinToString("") { String.format("%02x", it) }

    println(output)
}

fun getKnotHash(text: String): List<Int> {
    val input = text.map(Char::toInt) + postfix
    val initialState = State((0..255).toList(), 0, 0)
    val repeatedInput = (1..rounds).asSequence().map { input.asSequence() }.flatMap { it }
    val lastState = getLastState(repeatedInput.asIterable(), initialState)

    return lastState.list.chunked(16)
            .map { it.reduce(Int::xor) }
}