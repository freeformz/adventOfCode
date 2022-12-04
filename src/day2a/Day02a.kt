package day2a

import readInput

enum class Play(val value: Int, val opInput: Char, val myInput: Char) {
    Rock(1,'A','X') {
        override fun beats(o: Play) = o == Scissors
    },
    Paper(2,'B','Y') {
        override fun beats(o: Play) = o == Rock
    },
    Scissors(3,'C','Z') {
        override fun beats(o: Play) = o == Paper
    };
    abstract fun beats(o: Play): Boolean
}

val win = 6
val draw = 3

fun main() {
    val sum = readInput("day2").sumOf {
        val op = Play.values().first { pv ->
            pv.opInput == it.first()
        }
        val me = Play.values().first { pv ->
            pv.myInput == it.last()
        }
        if (me == op) {
            me.value + draw
        } else if (me.beats(op)) {
            me.value + win
        } else {
            me.value
        }
    }

    println(sum)
}
