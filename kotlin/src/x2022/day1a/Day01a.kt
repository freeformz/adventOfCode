package day1a

import readInput

fun main() {
    var currentElf = 0
    val max = readInput("day01").groupBy {// group values by elf
        if (it.isEmpty()) {
            currentElf++
        } else {
            currentElf
        }
    }.values.map { values -> // sum calories for each elf
        values.sumOf {
            it.ifEmpty { "0" }.toInt()
        }
    }.max()

    println(max)
}
