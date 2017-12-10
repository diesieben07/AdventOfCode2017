package de.take_weiland.adventofcode2017.ms1

/**
 * @author Take Weiland
 */
fun main(args: Array<String>) {
    println(solveCaptcha2(input))
}

fun solveCaptcha2(input: String): Int {
    return input.indices
            .filter { i -> input[i] == input[(i + input.length / 2).rem(input.length)] }
            .sumBy { input[it] - '0' }
}