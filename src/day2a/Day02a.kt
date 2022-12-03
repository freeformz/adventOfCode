package day2a

import readInput

enum class Play {
    Rock {
        override fun beats(o: Play) = o == Scissors
        override fun value() = 1
    },
    Paper {
        override fun beats(o: Play) = o == Rock
        override fun value() = 2
    },
    Scissors {
        override fun beats(o: Play) = o == Paper
        override fun value() = 3
    };
    abstract fun beats(o: Play): Boolean
    abstract fun value(): Int
}

val win = 6
val draw = 3

fun main() {
    val sum = readInput("day2").sumOf {
        val op = when (it.first()) {
            'A' -> Play.Rock
            'B' -> Play.Paper
            'C' -> Play.Scissors
            else -> throw Exception("unknown input")
        }
        val me = when (it.last()) {
            'X' -> Play.Rock
            'Y' -> Play.Paper
            'Z' -> Play.Scissors
            else -> throw Exception("unknown input")
        }
        if (me == op) {
            me.value() + draw
        } else if (me.beats(op)) {
            me.value() + win
        } else {
            me.value()
        }
    }

    println(sum)
}
