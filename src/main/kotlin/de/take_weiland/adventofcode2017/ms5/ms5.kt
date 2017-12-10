package de.take_weiland.adventofcode2017.ms5

import com.google.common.io.Resources

/**
 * @author Take Weiland
 */
fun main(args: Array<String>) {
    val list = parseList(Resources.getResource("de/take_weiland/adventofcode2017/ms5/input.txt").readText())
    println("jumps until outside: ${countJumpsUntilOutside(list)}")
    println("jumps until outside 2: ${countJumpsUntilOutside2(list)}")
}

fun parseList(input: String): List<Int> {
    return input.split(Regex("[\\r\\n]+")).map { it.toInt() }
}

fun countJumpsUntilOutside(list: List<Int>): Int {
    val mutable = list.toMutableList()
    var index = 0
    var count = 0
    while (index in mutable.indices) {
        val jump = mutable[index]
        mutable[index]++
        index += jump
        count++
    }
    return count
}

fun countJumpsUntilOutside2(list: List<Int>): Int {
    val mutable = list.toMutableList()
    var index = 0
    var count = 0
    while (index in mutable.indices) {
        val jump = mutable[index]
        mutable[index] += if (jump >= 3) -1 else 1
        index += jump
        count++
    }
    return count
}