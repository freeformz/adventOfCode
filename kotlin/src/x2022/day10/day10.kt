package day10

import readInput

enum class Instruction(val cycles: Int) {
    NOOP(1),
    ADDX(2);
}

data class Op(val instruction: Instruction, val data: Any?) {
    var cc: Int = 0
    fun cycle(cpu: CPU, data: Any?): Boolean {
        if (cc < instruction.cycles) cc++
        when (instruction) {
            Instruction.NOOP -> {}
            Instruction.ADDX -> {
                if (cc == instruction.cycles) {
                    //println("before x: ${cpu.registers["X"]}")
                    cpu.registers["X"] = cpu.registers["X"]!! + data as Int
                    //println("after x: ${cpu.registers["X"]}")
                }
            }
        }
        return cc == instruction.cycles
    }

}

class CPU {
    lateinit var registers: MutableMap<String, Int>
    var cycle: Int = 1
    var programCounter: Int = 0
    var cOp: Op? = null
    private var program = listOf<Op>()

    init {
        reset()
    }

    fun load(program: List<Op>) {
        this.program = program
    }

    fun executeUntil(endCycle: Int, crt: CRTRow?) {
        while (cycle < endCycle) {
            //println("start cycle: $cycle")
            if (programCounter > program.count() - 1) {
                break
            }
            if (cOp == null) {
                cOp = program[programCounter]
            }

            crt?.drawPixel(cycle - 1, regX())

            val done = cOp!!.cycle(this, cOp!!.data)
            cycle++
            if (done) {
                cOp = null
                programCounter++
            }
        }
    }

    fun regX(): Int {
        return registers["X"]!!
    }

    fun reset() {
        registers = mutableMapOf(Pair("X", 1))
        cycle = 1
        programCounter = 0
        cOp = null
    }
}

class CRTRow(val offset: Int = 0) {
    var row: String = ""

    fun drawPixel(pos: Int, spritePos: Int) {
        row += if (pos - offset in spritePos - 1..spritePos + 1) "#" else "."
    }
}

fun main() {
    fun parseInput(input: List<String>): List<Op> {
        return input.map {
            val parts = it.split(" ")
            when (parts.first()) {
                "noop" -> Op(Instruction.NOOP, null)
                "addx" -> Op(Instruction.ADDX, parts.last().toInt())
                else -> throw Exception("Unknown program instruction: ${parts.first()}")
            }
        }
    }

    fun partOne(input: List<String>) {
        val program = parseInput(input)
        val puter = CPU()
        puter.load(program)
        var sum = 0
        for (i in 20 until 221 step 40) {
            puter.executeUntil(i, null)
            val css = puter.regX() * i
            println("cycle $i: x=${puter.regX()} signal strength= ${css}")
            sum += css
        }
        println("signal strength sum: $sum")
    }

    fun partTwo(input: List<String>) {
        val program = parseInput(input)
        val puter = CPU()
        puter.load(program)
        for (offset in 1 until 241 step 40) {
            val crtRow = CRTRow(offset - 1)
            for (i in 0..40) {
                puter.executeUntil(offset + i, crtRow)
            }
            println(crtRow.row)
        }
    }

    println("partOne test")
    partOne(readInput("day10.test"))
    println()

    println("partOne")
    partOne(readInput("day10"))
    println()

    println("partTwo with test input")
    partTwo(readInput("day10.test"))
    println()

    println("partTwo")
    partTwo(readInput("day10"))

}