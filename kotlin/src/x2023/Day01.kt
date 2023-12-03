package x2023

open class Day01AExample {
    open val inputLines: List<String> = read2023Input("day01a-example")
    open val final = 142
    open var answer: Int = 0
    fun run() {
        println(this.javaClass.name)
        answer = inputLines.sumOf { line ->
            val digits = line.filter { c ->
                c.isDigit()
            }
            "${digits.first()}${digits.last()}".toInt()
        }
        println(answer)
    }

    fun check() {
        assert(answer == final)
        println()
    }
}

class Day01A : Day01AExample() {
    override val inputLines: List<String> = read2023Input("day01a")
    override val final = 54916
    override var answer: Int = 0
}

data class MInt(val s: String, val v: Int)

open class Day01BExample {
    open val inputLines: List<String> = read2023Input("day01b-example")
    open val final = 281
    open var answer: Int = 54728

    private val findValues = listOf(
        MInt("one", 1),
        MInt("two", 2),
        MInt("three", 3),
        MInt("four", 4),
        MInt("five", 5),
        MInt("six", 6),
        MInt("seven", 7),
        MInt("eight", 8),
        MInt("nine", 9),
        MInt("1", 1),
        MInt("2", 2),
        MInt("3", 3),
        MInt("4", 4),
        MInt("5", 5),
        MInt("6", 6),
        MInt("7", 7),
        MInt("8", 8),
        MInt("9", 9)
    )

    fun run() {
        println(this.javaClass.name)
        answer = inputLines.sumOf { line ->
            val first = findValues.mapNotNull {
                when (val idx = line.indexOf(it.s)) {
                    -1 -> null
                    else -> Pair(idx, it)
                }
            }.minBy { it.first }
            val last = findValues.mapNotNull {
                when (val idx = line.lastIndexOf(it.s)) {
                    -1 -> null
                    else -> Pair(idx, it)
                }
            }.maxBy { it.first }

            "${first.second.v}${last.second.v}".toInt()
        }
        println(answer)
    }

    fun check() {
        assert(answer == final)
        println()
    }
}

class Day01B : Day01BExample() {
    override val inputLines: List<String> = read2023Input("day01b")
    override val final = 54728
    override var answer: Int = 0
}

fun main() {
    val day1aExample = Day01AExample()
    day1aExample.run()
    day1aExample.check()

    val day1a = Day01A()
    day1a.run()
    day1a.check()

    val day1bExample = Day01BExample()
    day1bExample.run()
    day1bExample.check()

    val day1b = Day01B()
    day1b.run()
    day1b.check()
}
