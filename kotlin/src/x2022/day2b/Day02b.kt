package day2b

import readInput

enum class Outcome(val input: Char, val value: Int) {
    Lose('X', 0) {
        override fun score(op: Play): Int = op.winsAgainst().value + value
    },
    Draw('Y', 3) {
        override fun score(op: Play): Int = op.value + value
    },
    Win('Z', 6) {
        override fun score(op: Play): Int = op.loosesAgainst().value + value
    };

    abstract fun score(op: Play): Int
}

enum class Play(val value: Int, val opInput: Char) {
    Rock(1, 'A') {
        override fun beats(o: Play) = o == Rock.winsAgainst()
        override fun winsAgainst() = Scissors
        override fun loosesAgainst(): Play = Paper
    },
    Paper(2, 'B') {
        override fun beats(o: Play) = o == Paper.winsAgainst()
        override fun winsAgainst() = Rock
        override fun loosesAgainst(): Play = Scissors
    },
    Scissors(3, 'C') {
        override fun beats(o: Play) = o == Scissors.winsAgainst()
        override fun winsAgainst() = Paper
        override fun loosesAgainst(): Play = Rock
    };

    abstract fun winsAgainst(): Play
    abstract fun loosesAgainst(): Play
    abstract fun beats(o: Play): Boolean
}

fun main() {
    val sum = readInput("day2").sumOf { input ->
        val op = Play.values().first { pv ->
            pv.opInput == input.first()
        }
        val outcome = Outcome.values().first { out ->
            out.input == input.last()
        }
        outcome.score(op)
    }

    println(sum)
}
