package de.take_weiland.adventofcode2017.ms16

import de.take_weiland.adventofcode2017.getInput
import java.util.*

fun main(args: Array<String>) {
    val instructions = parseInstructions(getInput(16)).toList()
    println(instructions.size)
//    println(instructions.joinToString("\n"))
//    exitProcess(0)

    val start = ('a'..'p').joinToString("")

    val result = instructions.asIterable().execute(start)
    println(result)

    val repeated = (1..28).asSequence().flatMap { instructions.asSequence() }
    println(repeated.execute(start))
}

private fun Sequence<Instruction>.execute(initialState: String) = asIterable().execute(initialState)

private fun Iterable<Instruction>.execute(initialState: String): String {
    return fold(initialState) { current, instruction -> instruction.execute(current) }
}

private fun parseInstructions(input: String): Sequence<Instruction> {
    return input.splitToSequence(",")
            .map { parseInstruction(it) }
}

private val spinPattern = Regex("^s([0-9]+)$")
private val exchangePattern = Regex("^x([0-9]+)/([0-9]+)$")
private val partnerPattern = Regex("^p([a-p])/([a-p])$")

private fun parseInstruction(input: String): Instruction {
    val spinMatch = spinPattern.matchEntire(input)
    if (spinMatch != null) {
        return Instruction.Spin(spinMatch.groupValues[1].toInt())
    }
    val exchangeMatch = exchangePattern.matchEntire(input)
    if (exchangeMatch != null) {
        return Instruction.Exchange(exchangeMatch.groupValues[1].toInt(), exchangeMatch.groupValues[2].toInt())
    }
    val partnerMatch = partnerPattern.matchEntire(input)
    if (partnerMatch != null) {
        return Instruction.Partner(partnerMatch.groupValues[1][0], partnerMatch.groupValues[2][0])
    }

    throw Exception("Could not parse instruction $input")
}

private fun String.swap(pos1: Int, pos2: Int): String {
    val result = StringBuilder(this)
    val temp = result[pos1]
    result[pos1] = result[pos2]
    result[pos2] = temp
    return result.toString()
}

sealed class Instruction {

    abstract fun execute(state: String): String

    abstract fun <T> execute(order: List<T>): List<T>

    data class Spin(private val size: Int) : Instruction() {
        override fun execute(state: String): String {
            return buildString(state.length) {
                append(state, state.length - size, state.length)
                append(state, 0, state.length - size)
            }
        }

        override fun <T> execute(order: List<T>): List<T> {
            return order.subList(order.size - size, order.size) + order.subList(0, order.size - size)
        }
    }

    data class Exchange(private val pos1: Int, private val pos2: Int) : Instruction() {
        override fun execute(state: String): String {
            return state.swap(pos1, pos2)
        }

        override fun <T> execute(order: List<T>): List<T> {
            val result = order.toMutableList()
            Collections.swap(result, pos1, pos2)
            return result
        }
    }

    data class Partner(private val prg1: Char, private val prg2: Char): Instruction() {

        override fun execute(state: String): String {
            return state.swap(state.indexOf(prg1), state.indexOf(prg2))
        }

        override fun <T> execute(order: List<T>): List<T> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

}