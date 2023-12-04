package x2023

open class Day04AExample {
    open val inputLines: List<String> = read2023Input("day04-example")
    open val expected = 13
    open var answer: Int = 0

    fun run() {
        answer = inputLines.sumOf { line ->
            val lines = line.split(":")
            val card = lines[0]
            val numbers = lines[1].split("|")
            val winning = numbers[0].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
            val mine = numbers[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
            val won = winning.intersect(mine)
            when (val count = won.count()) {
                0 -> 0
                else -> (count - 1).downTo(1).fold(1) { acc, _ -> acc * 2 }
            } as Int

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

class Day04A : Day04AExample() {
    override val inputLines: List<String> = read2023Input("day04")
    override val expected = 18519
}

open class Day04BExample {
    open val inputLines: List<String> = read2023Input("day04-example")
    open val expected = 467835
    open var answer: Int = 0

    fun run() {
        var numbers = mutableListOf<Entry>()
        var symbols = mutableListOf<Symbol>()
        inputLines.forEachIndexed { lidx, line ->
            var cnum = 0
            var numStart = -1
            var constuctingNumber = false

            line.forEachIndexed { cidx, char ->
                if (char.isDigit()) {
                    if (!constuctingNumber) {
                        numStart = cidx
                    }
                    cnum = cnum * 10 + char.toString().toInt()
                    constuctingNumber = true
                } else {
                    if (constuctingNumber) {
                        constuctingNumber = false
                        numbers.add(Entry(cnum, lidx, numStart, cidx - 1))
                        cnum = 0
                        numStart = -1
                    }
                    if (char != '.') {
                        symbols.add(Symbol(char, cidx, lidx))
                    }
                }
            }
            if (constuctingNumber) { // number at end of the line
                numbers.add(Entry(cnum, lidx, numStart, line.length - 1))
            }
        }

        answer = symbols.filter { symb ->
            symb.char == '*'
        }.sumOf { symb ->
            symb.gearRatio(numbers)
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

class Day04B : Day04BExample() {
    override val inputLines: List<String> = read2023Input("day04")
    override val expected = 87287096
}

fun main() {
    Day04AExample().also {
        it.run()
        it.check()
    }

    Day04A().also {
        it.run()
        it.check()
    }

    Day04BExample().also {
        it.run()
        it.check()
    }

    Day04B().also {
        it.run()
        it.check()
    }
}