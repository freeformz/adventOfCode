package day5

import readInput

fun parseStackInput(stackInput: List<String>): List<ArrayDeque<Char>> {
    val stacks = stackInput[0].split(" ").mapNotNull { it.ifEmpty { null }?.toInt() }.map {
        ArrayDeque<Char>()
    }
    stackInput.drop(1).forEach {
        it.chunked(4).forEachIndexed { i, its ->
            val c = its[1]
            if (!c.isWhitespace()) {
                stacks[i].addLast(c)
            }
        }
    }

    return stacks
}

fun parseInput(input: List<String>): Pair<List<String>, List<String>> {
    var moves = false
    val parts = input.groupBy {
        if (it.isEmpty()) {
            moves = true
        }
        when (moves) {
            true -> if (it.isEmpty()) "empty" else "moves"
            false -> "stacks"
        }
    }
    return Pair(
            parts["stacks"]?.reversed() ?: throw Exception("invalid input"),
            parts["moves"] ?: throw Exception("invalid input")
    )
}

fun partOne(stacks: List<ArrayDeque<Char>>, movesInput: List<String>) {
    for (mi in movesInput) {
        val mp = mi.split(" ")
        val qty = mp[1].toInt()
        val from = mp[3].toInt()
        val to = mp[5].toInt()
        val src = stacks[from - 1]
        val dst = stacks[to - 1]
        for (i in 1..qty) {
            dst.addLast(src.removeLast())
        }
    }

    println(stacks.fold("") { out, stack -> out + stack.last() })
}

fun partTwo(stacks: List<ArrayDeque<Char>>, movesInput: List<String>) {
    for (mi in movesInput) {
        val mp = mi.split(" ")
        val qty = mp[1].toInt()
        val from = mp[3].toInt()
        val to = mp[5].toInt()
        val src = stacks[from - 1]
        val dst = stacks[to - 1]
        val tmp = ArrayDeque<Char>()
        for (i in 1..qty) {
            tmp.addFirst(src.removeLast())
        }
        for (i in tmp) {
            dst.addLast(i)
        }
    }

    println(stacks.fold("") { out, stack -> out + stack.last() })
}

fun main() {
    val (stackInput, movesInput) = parseInput(readInput("day5"))

    partOne(parseStackInput(stackInput), movesInput)
    partTwo(parseStackInput(stackInput), movesInput)
}