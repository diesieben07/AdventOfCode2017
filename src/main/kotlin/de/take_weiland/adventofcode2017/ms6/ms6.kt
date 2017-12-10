package de.take_weiland.adventofcode2017.ms6

/**
 * @author Take Weiland
 */
private val input = "4\t1\t15\t12\t0\t9\t9\t5\t5\t8\t7\t3\t14\t5\t12\t3"

fun main(args: Array<String>) {
    println(getCyclesUntilRepeat(parseBanks(input)))
    println(getCyclesUntilRepeat(listOf(0, 2, 7, 0)))
}

private fun parseBanks(input: String): List<Int> {
    return input.split(Regex("\\s+"))
            .map { it.toInt() }
}

fun getCyclesUntilRepeat(banks: List<Int>): Pair<Int, Int> {
    val seenWhen = mutableMapOf<List<Int>, Int>()
    var cycle = 0
    var currentBanks = banks
    do {
        seenWhen[currentBanks]?.let { return Pair(cycle, cycle - it) }

        seenWhen[currentBanks] = cycle
        cycle++
        currentBanks = getNextConfiguration(currentBanks)
    } while (true)
}

fun getNextConfiguration(banks: List<Int>): List<Int> {
    var (index, left) = banks.withIndex().maxBy { it.value } ?: throw Exception()
    val newBanks = banks.toMutableList()
    newBanks[index] = 0
    while (left > 0) {
        index++
        newBanks[index.rem(newBanks.size)]++
        left--
    }
    return newBanks
}