package day3a

import readInput

fun main() {
    val sum = readInput("day3").map { it.chunked(it.length / 2) }.map {
        check(it.first().length == it.last().length)
        val set = it.first().toSet().intersect(
            it.last().toSet()
        )
        check(set.count() == 1)
        val char = set.first()
        when {
            // priority value calculated against ascii code
            // a = 97 (ascii value) - 96 = 1 | A = 65 (ascii value) - 38 = 27
            char.isUpperCase() -> char.code - 38
            char.isLowerCase() -> char.code - 96
            else -> throw Exception("unknown value: $char")
        }
    }.sumOf {
        it
    }
    println(sum)
}