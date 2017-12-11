package de.take_weiland.adventofcode2017

import com.google.common.io.Resources

/**
 * @author Take Weiland
 */
fun getInput(ms: Int): String {
    return Resources.getResource("de/take_weiland/adventofcode2017/ms$ms/input.txt").readText()
}