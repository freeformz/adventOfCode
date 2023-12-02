package day11

import readInput

enum class MathOperation {
    Multiply,
    Divide,
    Subtract,
    Add;
}

// -1 == old value
data class Operation(val op: MathOperation, val value: Long, val useOld: Boolean) {
    fun compute(input: Long): Long {
        val v = when (useOld) {
            true -> input
            else -> value
        }
        return when (op) {
            MathOperation.Multiply -> input * v
            MathOperation.Divide -> input / v
            MathOperation.Add -> input + v
            MathOperation.Subtract -> input - v
        }
    }
}

data class Monkey(
    val items: MutableList<Long>,
    val operation: Operation,
    val test: Operation,
    val targets: Pair<Int, Int>
) {
    var inspected = 0
}

fun lcm(divisors: List<Long>): Long {
    var lcm = divisors.maxOf { it }
    while (true) {
        if (divisors.all { lcm % it == 0.toLong() }) break else lcm++
    }
    return lcm
}

class Barrel(private val monkeys: List<Monkey>) {
    private val lcm = lcm(monkeys.map { it.test.value })
    fun play(relief: Int) {
        for (monkey in monkeys) {
            val oldItems = monkey.items.toList()
            monkey.items.clear()
            for (item in oldItems) {
                monkey.inspected++
                var newItem = monkey.operation.compute(item)
                if (relief > 1) {
                    newItem = monkey.operation.compute(item) / relief
                }
                if (newItem % monkey.test.value == 0.toLong()) {
                    monkeys[monkey.targets.first].items.add(newItem % lcm)
                } else {
                    monkeys[monkey.targets.second].items.add(newItem % lcm)
                }
            }
        }
    }

    fun monkeyBusiness(): Long {
        return monkeys.sortedBy { it.inspected }.takeLast(2).map { it.inspected.toLong() }.reduce { a, b -> a * b }
    }
}

fun main() {
    fun parseInput(input: List<String>): List<Monkey> {
        return input.map { it.split(" ").filter { i -> i.isNotEmpty() } }.chunked(7).map { parts ->
            Monkey(
                items = parts[1].mapNotNull {
                    it.split(",").first().toLongOrNull()
                }.toMutableList(),
                operation = Operation(
                    op = when (parts[2][4]) {
                        "*" -> MathOperation.Multiply
                        "+" -> MathOperation.Add
                        else -> throw Exception("op input error: ${parts[2]}")
                    },
                    value = if (parts[2][5] == "old") 0.toLong() else parts[2][5].toLong(),
                    useOld = parts[2][5] == "old",
                ),
                test = Operation(
                    op = when (parts[3][1]) {
                        "divisible" -> MathOperation.Divide
                        else -> throw Exception("test input error: ${parts[3]}")
                    },
                    value = parts[3][3].toLong(),
                    useOld = false,
                ),
                targets = Pair(
                    parts[4].last().toInt(),
                    parts[5].last().toInt()
                )
            )
        }
    }

    fun partOne(input: List<String>) {
        val monkeys = parseInput(input)
        val barrel = Barrel(monkeys)
        for (i in 1..20) {
            barrel.play(3)
        }
        for (monkey in monkeys) {
            println("inspected: ${monkey.inspected} - $monkey")
        }
        println("monkey business = ${barrel.monkeyBusiness()}")
    }

    fun partTwo(input: List<String>) {
        val monkeys = parseInput(input)
        val barrel = Barrel(monkeys)
        for (i in 1..10000) {
            barrel.play(1)
        }
        for (monkey in monkeys) {
            println("inspected: ${monkey.inspected} - $monkey")
        }
        println("monkey business = ${barrel.monkeyBusiness()}")
    }

    println("partOne test")
    partOne(readInput("day11.test"))
    println()

    println("partOne")
    partOne(readInput("day11"))
    println()

    println("partTwo with test input")
    partTwo(readInput("day11.test"))
    println()

    println("partTwo")
    partTwo(readInput("day11"))

}