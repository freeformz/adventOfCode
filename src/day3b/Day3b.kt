package day3b

import readInput

fun main() {
    val sum = readInput("day3").chunked(3).map { group ->
        group.reduce { a, b ->
            a.toSet().intersect(b.toSet()).toString()
        }.filter{
            it.isLetter()
        }
    }.sumOf {
        check(it.count() == 1)
        when (val char = it.first()) {
            in 'a'..'z' -> char.code - 96
            in 'A'..'Z' -> char.code - 38
            else -> throw Exception("unknown value")
        }
    }
    println(sum)
}