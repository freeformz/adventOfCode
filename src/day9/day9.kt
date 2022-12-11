package day9

import readInput
import kotlin.math.absoluteValue

enum class Direction {
    Right, Left, Up, Down
}

data class Position(val x: Int, val y: Int)
data class Move(val direction: Direction, val distance: Int)

class Grid(parts: Int) {
    val ropeParts = MutableList(parts) { Position(0, 0) }
    val visited = hashSetOf<Position>()

    fun execute(move: Move) {
        //println(move)
        for (i in 1..move.distance) {
            val head = ropeParts.first()
            var x = head.x
            var y = head.y
            when (move.direction) {
                Direction.Right -> x += 1
                Direction.Left -> x -= 1
                Direction.Up -> y += 1
                Direction.Down -> y -= 1
            }
            ropeParts[0] = Position(x, y)
            for (i in 0..ropeParts.count() - 2) {
                val ntail = moveNext(move.direction, ropeParts[i], ropeParts[i + 1])
                // println("i: $i, head: ${ropeParts[i]} tail: ${ropeParts[i + 1]} ntail: $ntail")
                if ((i + 1) == (ropeParts.count() - 1)) {
                    //println("added")
                    visited.add(ntail)
                }
                ropeParts[i + 1] = ntail
            }
            //println(ropeParts)
        }
    }

    private fun moveNext(direction: Direction, head: Position, tail: Position): Position {
        val xd = head.x - tail.x
        val yd = head.y - tail.y
        if (xd.absoluteValue > 1 || yd.absoluteValue > 1) {
            val xm = if (xd < -1) {
                xd + 1
            } else if (xd > 1) {
                xd - 1
            } else {
                xd
            }
            val ym = if (yd < -1) {
                yd + 1
            } else if (yd > 1) {
                yd - 1
            } else {
                yd
            }
            return Position(tail.x + xm, tail.y + ym)
        }
        return tail
    }
}

fun main() {
    fun parseInput(input: List<String>): List<Move> {
        return input.map {
            val parts = it.split(" ")
            val dir = when (parts.first()) {
                "U" -> Direction.Up
                "D" -> Direction.Down
                "R" -> Direction.Right
                "L" -> Direction.Left
                else -> throw Exception("invalid input: " + parts.first())
            }
            Move(dir, parts.last().toInt())
        }
    }

    fun partOne(input: List<String>) {
        val grid = Grid(2)
        parseInput(input).forEach {
            grid.execute(it)
        }
        println(grid.visited.count())
        println(grid.ropeParts)
    }

    fun partTwo(input: List<String>) {
        val grid = Grid(10)
        parseInput(input).forEach {
            grid.execute(it)
        }
        println(grid.visited.count())
        println(grid.visited)
    }

    println("partOne test")
    partOne(readInput("day9.test"))
    println()

    println("partOne")
    partOne(readInput("day9"))
    println()
    
    println("partTwo with partTwo test input")
    partTwo(readInput("day9-part2.test"))
    println()

    println("partTwo")
    partTwo(readInput("day9"))
}