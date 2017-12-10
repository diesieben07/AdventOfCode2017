package de.take_weiland.adventofcode2017.ms2

val testData2 = "5 9 2 8\n" +
        "9 4 7 3\n" +
        "3 8 6 5"

/**
 * @author Take Weiland
 */
fun main(args: Array<String>) {
    println(getSpreadsheetDivisionResultSum(parseSpreadsheet(data)))
}

fun getSpreadsheetDivisionResultSum(spreadsheet: List<List<Int>>): Int {
    return spreadsheet.map { row -> getDivisionResult(row) }.sum()
}

fun getDivisionResult(row: List<Int>): Int {
    for (i in row.indices) {
        for (j in row.indices) {
            if (i == j) continue
            val x = row[i]
            val y = row[j]

            if (x.rem(y) == 0) {
                return x / y
            }
        }
    }
    throw Exception("No two values divide in row $row")
}

fun <T> List<T>.allPermutations(): List<List<T>> {
    val target = ArrayList<List<T>>()
    addAllPermutations(emptyList(), this, target)
    return target
}

fun <T> addAllPermutations(startWith: List<T>, list: List<T>, target: MutableList<List<T>>) {
    when (list.size) {
        1 -> target.add(startWith + list)
        2 -> {
            target.add(startWith + list)
            target.add(startWith + list.asReversed())
        }
        else -> {
            for (start in list) {
                val newStartWith = startWith + start
                val newList = list.filter { it != start }
                addAllPermutations(newStartWith, newList, target)
            }
        }
    }
}