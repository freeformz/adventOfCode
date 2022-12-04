package day3b

import readInput

fun main() {
    val sum = readInput("day3").chunked(3).map { group ->
        group.reduce { a, b ->
            a.toSet().intersect(b.toSet()).toString()
        }.filter { it.isLetter() }
    }.sumOf {
        check(it.count() == 1)
        val char = it.first()
        when {
            // priority value calculated against ascii code
            // a = 97 (ascii value) - 96 = 1 | A = 65 (ascii value) - 38 = 27
            char.isUpperCase() -> char.code - 38
            char.isLowerCase() -> char.code - 96
            else -> throw Exception("unknown value: $char")
        }
    }
    println(sum)
}