package day12

import readInput

typealias Point = Pair<Int, Int>

fun Point.neighbours() = sequenceOf(
    first - 1 to second,
    first to second + 1,
    first + 1 to second,
    first to second - 1
)

fun main() {
    fun parseInput(input: List<String>): Triple<Point, Point, Map<Point, Char>> {
        lateinit var start: Point
        lateinit var end: Point

        val parsed = input.withIndex().flatMap { (y, line) ->
            line.withIndex().map { (x, char) ->
                val point = y to x
                when (char) {
                    'S' -> point to 'a'.also { start = point }
                    'E' -> point to 'z'.also { end = point }
                    else -> point to char
                }
            }
        }.toMap()

        return Triple(start, end, parsed)
    }

    fun countPaths(end: Point, parsed: Map<Point, Char>): Map<Point, Int> {
        return buildMap {
            var count = 0
            var candidates = setOf(end)
            while (candidates.isNotEmpty()) {
                candidates = buildSet {
                    for (candidate in candidates) {
                        if (putIfAbsent(candidate, count) != null) continue
                        val value = parsed.getValue(candidate)
                        for (neighbour in candidate.neighbours()) {
                            parsed[neighbour]?.also {
                                if (value - it <= 1) {
                                    add(neighbour)
                                }
                            }
                        }
                    }
                }
                count++
            }
        }
    }

    fun partOne(input: List<String>) {
        val (start, end, parsed) = parseInput(input)
        val countPaths = countPaths(end, parsed)

        println(countPaths[start])
    }

    fun partTwo(input: List<String>) {
        val (_, end, parsed) = parseInput(input)
        val countPaths = countPaths(end, parsed)

        println(parsed.filterValues('a'::equals).filterKeys(countPaths::containsKey).keys.minOf(countPaths::getValue))
    }

    println("partOne test")
    partOne(readInput("day12.test"))
    println()

    println("partOne")
    partOne(readInput("day12"))
    println()

    println("partTwo with test input")
    partTwo(readInput("day12.test"))
    println()

    println("partTwo")
    partTwo(readInput("day12"))
}