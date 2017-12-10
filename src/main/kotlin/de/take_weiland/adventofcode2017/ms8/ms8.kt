package de.take_weiland.adventofcode2017.ms8

import com.google.common.io.Resources

/**
 * @author Take Weiland
 */
private val test = "b inc 5 if a > 1\n" +
        "a inc 1 if b < 5\n" +
        "c dec -10 if a >= 1\n" +
        "c inc -20 if c == 10"

fun main(args: Array<String>) {
//    val instructions = parseInput(test)
    val instructions = parseInput(Resources.getResource("de/take_weiland/adventofcode2017/ms8/input.txt").readText())

    val initialData = mapOf<String, Int>()
    val (data, dataList) = instructions.fold(Pair(initialData, listOf(initialData))) { (data, dataList), instruction ->
        val nextData = instruction.execute(data)
        Pair(nextData, dataList + nextData)
    }
    println("max value at end: " + data.values.max())
    println("max value ever: " + dataList.flatMap { it.values }.max())

}

private fun Instruction.execute(data: Map<String, Int>): Map<String, Int> {
    val conditionRegisterValue = data[conditionRegister] ?: 0
    val execute = when (condition) {
        "==" -> conditionRegisterValue == conditionValue
        "!=" -> conditionRegisterValue != conditionValue
        ">=" -> conditionRegisterValue >= conditionValue
        "<=" -> conditionRegisterValue <= conditionValue
        ">" -> conditionRegisterValue > conditionValue
        "<" -> conditionRegisterValue < conditionValue
        else -> throw Exception("Unknown condition operator $condition")
    }
    return if (execute) {
        data + Pair(register, (data[register] ?: 0) + add)
    } else {
        data
    }
}

private data class Instruction(val register: String, val add: Int, val condition: String, val conditionRegister: String, val conditionValue: Int)

private fun parseInput(input: String): List<Instruction> {
    return Regex("^([a-z]+)\\s+((?:inc)|(?:dec))\\s+(-?[0-9]+)\\s+if\\s+([a-z]+)\\s+([<>!=]=?)\\s+(-?[0-9]+)\$", RegexOption.MULTILINE)
            .findAll(input)
            .map { match ->
                Instruction(
                        match.groupValues[1],
                        match.groupValues[3].toInt() * if (match.groupValues[2] == "inc") 1 else -1,
                        match.groupValues[5],
                        match.groupValues[4],
                        match.groupValues[6].toInt()
                )
            }.toList()
}