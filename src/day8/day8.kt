package day8

import readInput

enum class Direction {
    Up, Down, Right, Left
}

data class Tree(val height: Int, val views: Map<Direction, List<Int>>) {
    fun visible(): Boolean {
        return views.any { (_, v) -> v.isEmpty() || v.all { it < height } }
    }

    fun locationValue(): Int {
        return views.map { (k, v) ->
            val i = v.indexOfFirst { it >= height }
            val check = if (i == -1) v else v.subList(0, i + 1)
            check.count()
        }.reduce { acc, v ->
            acc * v
        }
    }
}

fun main() {
    fun parseInput(input: List<String>): List<Tree> {
        val matrix = input.map { it.chunked(1).map { it.toInt() } }
        val trees = mutableListOf<Tree>()
        for (x in 0 until matrix[0].count()) {
            for (y in 0 until matrix.count()) {
                val views = mutableMapOf<Direction, List<Int>>()
                for (direction in Direction.values()) {
                    val view = mutableListOf<Int>()
                    when (direction) {
                        Direction.Left -> {
                            for (i in y - 1 downTo 0) {
                                view.add(matrix[x][i])
                            }
                            views[Direction.Left] = view
                        }

                        Direction.Right -> {
                            for (i in y + 1 until matrix.count()) {
                                view.add(matrix[x][i])
                            }
                            views[Direction.Right] = view
                        }

                        Direction.Down -> {
                            for (i in x + 1 until matrix[0].count()) {
                                view.add(matrix[i][y])
                            }
                            views[Direction.Down] = view
                        }

                        Direction.Up -> {
                            for (i in x - 1 downTo 0) {
                                view.add(matrix[i][y])
                            }
                            views[Direction.Up] = view
                        }
                    }
                }
                trees.add(Tree(matrix[x][y], views))
            }
        }
        return trees
    }

    fun partOne(input: List<String>) {
        println(
            parseInput(input).sumOf {
                if (it.visible()) 1 else 0 as Int
            }
        )
    }

    fun partTwo(input: List<String>) {
        println(
            parseInput(input).map { Pair(it.locationValue(), it) }.maxBy {
                it.first
            }
        )
    }

    println("partOne test")
    partOne(readInput("day8.test"))
    println()

    println("partOne")
    partOne(readInput("day8"))
    println()

    println("partTwo test")
    partTwo(readInput("day8.test"))
    println()

    println("partTwo")
    partTwo(readInput("day8"))
}