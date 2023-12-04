package x2023

data class Entry(val num: Int, val line: Int, val start: Int, val end: Int) {
    fun isPart(symbols: List<Symbol>): Boolean = symbols.any { symbol ->
        symbol.adjacent(this)
    }
}

data class Symbol(val char: Char, val x: Int, val y: Int) {

    fun adjacent(num: Entry): Boolean = y >= num.line - 1 && y <= num.line + 1 && x >= num.start - 1 && x <= num.end + 1

    fun gearRatio(numbers: List<Entry>): Int {
        val adj = numbers.filter { num ->
            this.adjacent(num)
        }
        if (adj.count() == 2) {
            return adj[0].num * adj[1].num
        }
        return 0
    }
}


open class Day03AExample {
    open val inputLines: List<String> = read2023Input("day03a-example")
    open val expected = 4361
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

        answer = numbers.sumOf { num ->
            if (num.isPart(symbols)) {
                num.num
            } else {
                0
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

class Day03A : Day03AExample() {
    override val inputLines: List<String> = read2023Input("day03a")
    override val expected = 535351
}

open class Day03BExample {
    open val inputLines: List<String> = read2023Input("day03b-example")
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

class Day03B : Day03BExample() {
    override val inputLines: List<String> = read2023Input("day03b")
    override val expected = 87287096
}

fun main() {
    Day03AExample().also {
        it.run()
        it.check()
    }

    Day03A().also {
        it.run()
        it.check()
    }

    Day03BExample().also {
        it.run()
        it.check()
    }

    Day03B().also {
        it.run()
        it.check()
    }
}