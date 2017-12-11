package de.take_weiland.adventofcode2017.ms11

import de.take_weiland.adventofcode2017.getInput
import kotlin.math.absoluteValue

/**
 * @author Take Weiland
 */

private val directions = mapOf(
        "n" to listOf(1, 0, 0),
        "ne" to listOf(0, 1, 0),
        "se" to listOf(0, 0, 1),
        "s" to listOf(-1, 0, 0),
        "sw" to listOf(0, -1, 0),
        "nw" to listOf(0, 0, -1)
)

enum class Dir {
    N, NE, SE, S, SW, NW
}

typealias Coord = Pair<Int, Int>
typealias CubeCoord = Triple<Int, Int, Int>

fun main(args: Array<String>) {
//    val input = "ne,ne,ne".split(",")
    val input = getInput(11).split(",")

    val parsed = input.map { Dir.valueOf(it.toUpperCase()) }

    val start = Coord(0, 0)
    val coords = parsed.fold(listOf(start)) { coords, dir -> coords + getNextCoord(coords.last(), dir) }

    println(coords.last().distance(start))
    println(coords.map { it.distance(start) }.max())
}

fun getNextCoord(coord: Coord, dir: Dir): Coord {
    val (q, r) = coord
    return when (dir) {
        Dir.N -> Coord(q, r - 1)
        Dir.S -> Coord(q, r + 1)
        Dir.NE -> Coord(q + 1, r - 1)
        Dir.SE -> Coord(q + 1, r)
        Dir.SW -> Coord(q - 1, r + 1)
        Dir.NW -> Coord(q - 1, r)
    }
}

fun Coord.toCubeCoords(): CubeCoord {
    val (q, r) = this
    val x = q
    val z = r
    val y = -x - z
    return CubeCoord(x, y, z)
}

fun Coord.distance(other: Coord) = this.toCubeCoords().distance(other.toCubeCoords())

fun CubeCoord.distance(other: CubeCoord): Int {
    val (x1, y1, z1) = this
    val (x2, y2, z2) = other
    return ((x1 - x2).absoluteValue + (y1 - y2).absoluteValue + (z1 - z2).absoluteValue) / 2
}