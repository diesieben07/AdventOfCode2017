package de.take_weiland.adventofcode2017.ms4

import com.google.common.io.Resources

fun main(args: Array<String>) {
    val phrases = parsePassphrases(Resources.getResource("de/take_weiland/adventofcode2017/ms4/input.txt").readText())
    println("${countValidPassphrases(phrases)} passphrases are valid")

    val sortedPhrases = phrases.map { phrase -> phrase.map { word -> sortWordByLetter(word) } }
    println("${countValidPassphrases(sortedPhrases)} passphrases are valid after 2nd rule")
}

fun sortWordByLetter(word: String): String {
    return word.toSortedSet().joinToString(separator = "")
}

fun parsePassphrases(input: String): List<List<String>> {
    return input.split(Regex("[\\r\\n]+"))
            .map { parsePasshrase(it) }
}

fun parsePasshrase(input: String): List<String> {
    return input.split(Regex("\\s+"))
}

fun countValidPassphrases(passphrases: List<List<String>>): Int {
    return passphrases.count { it.isValidPassphrase() }
}

fun List<String>.isValidPassphrase(): Boolean {
    return distinct().size == size
}