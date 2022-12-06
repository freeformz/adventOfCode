package day6

import readInput

fun main() {
    fun firstRepeatingSetOfSize(input: List<String>, size: Int): List<Int> {
        var result = mutableListOf<Int>()
        input.forEach {
            var q = mutableListOf<Char>()
            for ((i, c) in it.withIndex()) {
                q.add(c)
                if (q.count() == size + 1) {
                    q.removeFirst()
                }
                if (q.count() == size) {
                    if (q.toSet().count() == size) {
                        result.add(i + 1)
                        break
                    }
                }
            }
        }
        return result
    }

    fun partOne(input: List<String>) {
        firstRepeatingSetOfSize(input, 4).forEach { println(it) }
    }

    fun partTwo(input: List<String>) {
        firstRepeatingSetOfSize(input, 14).forEach { println(it) }
    }

    println("partOne test")
    partOne(readInput("day6.test"))
    println()

    println("partOne")
    partOne(readInput("day6"))
    println()

    println("partTwo test")
    partTwo(readInput("day6.test"))
    println()

    println("partTwo")
    partTwo(readInput("day6"))
}