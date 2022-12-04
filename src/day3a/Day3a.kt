package day3a

import readInput

fun main() {
    val sum = readInput("day3").
    map {
        it.chunked(it.length/2)
    }.map {
        check(it.first().length == it.last().length)
        val set = it.first().toSet().intersect(
            it.last().toSet()
        )
        check(set.count() == 1)
        when (val char = set.first()) {
            // priority value calculated against ascii code
            // a = 97 (ascii value) - 96 = 1 | A = 65 (ascii value) - 38 = 27
            in 'a'..'z' -> char.code - 96
            in 'A'..'Z' -> char.code - 38
            else -> throw Exception("unknown value")
        }
    }.sumOf {
        println(it)
        it
    }
    println(sum)
}