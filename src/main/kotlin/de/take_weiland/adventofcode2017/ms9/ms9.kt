package de.take_weiland.adventofcode2017.ms9

import com.google.common.io.Resources

/**
 * @author Take Weiland
 */
fun main(args: Array<String>) {
//    val input = "{{<a!>},{<a!>},{<a!>},{<ab>}}"
    val input = Resources.getResource("de/take_weiland/adventofcode2017/ms9/input.txt").readText()
    println(getParseStream(input).last())
}

fun getParseStream(input: String): Sequence<ParseState> {
    return generateSequence(ParseState(0, 0, false, false, 0, 0, false)) { state ->
        parseNext(state, input).takeUnless { it.done }
    }
}

fun parseNext(current: ParseState, input: String): ParseState {
    with(current) {
        if (done) throw Exception("Already done.")

        if (offset !in input.indices) {
            if (inGarbage) throw Exception("Unexpected end of input inside garbage.")
            if (groupDepth != 0) throw Exception("Unexpected end of input, $groupDepth groups still open.")
            return copy(done = true)
        }

        if (canceled) return next(canceled = false)

        val char = input[offset]
        if (inGarbage) {
            return when (char) {
                '!' -> next(canceled = true)
                '>' -> next(inGarbage = false)
                else -> next(garbageCharCount = garbageCharCount + 1)
            }
        }

        return when (char) {
            '<' -> next(inGarbage = true)
            '{' -> next(groupDepth = groupDepth + 1)
            '}' -> next(groupDepth = groupDepth - 1, score = score + groupDepth)
            ',' -> next()
            else -> throw Exception("Unexpected character in group: $char")
        }
    }
}

data class ParseState(val offset: Int, val groupDepth: Int, val inGarbage: Boolean,
                      val canceled: Boolean, val score: Int,
                      val garbageCharCount: Int,
                      val done: Boolean) {

    fun next(groupDepth: Int = this.groupDepth, inGarbage: Boolean = this.inGarbage,
             canceled: Boolean = this.canceled, score: Int = this.score,
             garbageCharCount: Int = this.garbageCharCount,
             done: Boolean = this.done) =
            copy(offset = offset + 1, groupDepth = groupDepth, inGarbage = inGarbage,
                    canceled = canceled, score = score,
                    garbageCharCount = garbageCharCount,
                    done = done)

}