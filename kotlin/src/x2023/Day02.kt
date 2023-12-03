package x2023

data class Dice(var color: String, var count: Int)

fun List<Dice>.possible(set: List<Dice>): Boolean {
    return set.all { die ->
        this.any { d ->
            d.color == die.color && d.count >= die.count
        }
    }
}

open class Day02AExample {
    open val inputLines: List<String> = read2023Input("day02a-example")
    open val expected = 8
    open val allDice = listOf(Dice("red", 12), Dice("green", 13), Dice("blue", 14))
    open var answer: Int = 0

    fun run() {
        answer = inputLines.sumOf { line ->
            val p1 = line.split(":") // game number vs input
            val game = p1.first().split(" ").last().toInt() // game number
            when (val possible = p1.last().split(";").map { set -> // split into sets of dice
                set.trim().split(",").map { die -> // split into individual dice and map to Dice class
                    val p2 = die.trim().split(" ")
                    Dice(p2.last(), p2.first().toInt())
                }
            }.map { it -> allDice.possible(it) }.all { it }) { // determine if possible
                true -> game
                false -> 0
            }
        }
    }

    fun check() {
        println(this.javaClass.name)
        println("answer: $answer")
        println("expected: $expected")
        assert(answer == expected)
        println()
    }
}

class Day02A : Day02AExample() {
    override val inputLines: List<String> = read2023Input("day02a")
    override val expected = 1734
}

fun List<Dice>.minimum(set: List<Dice>): List<Dice> {
    return this.map { die ->
        val other = set.singleOrNull { d -> d.color == die.color }
        when (other) {
            null -> die
            else -> {
                listOf(die, other).maxBy(Dice::count)
            }
        }
    }
}

open class Day02BExample {
    open val inputLines: List<String> = read2023Input("day02b-example")
    open val expected = 2286
    open var answer: Int = 0

    fun run() {
        answer = inputLines.fold(0) { acc, line ->
            val p1 = line.split(":") // game number vs input
            val game = p1.first().split(" ").last().toInt() // game number
            val p = p1.last().split(";").map { set -> // split into sets of dice
                set.trim().split(",").map { die -> // split into individual dice and map to Dice class
                    val p2 = die.trim().split(" ")
                    Dice(p2.last(), p2.first().toInt())
                }
            }.fold(listOf(Dice("red", 0), Dice("green", 0), Dice("blue", 0))) { g, set ->
                g.minimum(set)
            }.fold(1) { mul, die ->
                mul * die.count
            }
            acc + p
        }
    }

    fun check() {
        println(this.javaClass.name)
        println("answer: $answer")
        println("expected: $expected")
        assert(answer == expected)
        println()
    }
}

class Day02B : Day02BExample() {
    override val inputLines: List<String> = read2023Input("day02b")
    override val expected = 70387
}

fun main() {
    Day02AExample().also {
        it.run()
        it.check()
    }

    Day02A().also {
        it.run()
        it.check()
    }

    Day02BExample().also {
        it.run()
        it.check()
    }

    Day02B().also {
        it.run()
        it.check()
    }
}