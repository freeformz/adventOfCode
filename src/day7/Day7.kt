package day7

import readInput

data class File(val name: String, val size: Int)

data class Dir(
    val name: String,
    val parent: Dir? = null,
    val children: MutableMap<String, Dir>,
    val files: MutableList<File>
) {
    fun size(): Int {
        return files.sumOf { it.size } + children.values.sumOf { it.size() }
    }

    fun childrenAtMost(size: Int): List<Dir> {
        return children.values.filter { c ->
            c.size() <= size
        } + children.values.flatMap { c ->
            c.childrenAtMost(size)
        }
    }

    fun childrenAtLeast(size: Int): List<Dir> {
        return children.values.filter { c ->
            c.size() >= size
        } + children.values.flatMap { c ->
            c.childrenAtLeast(size)
        }
    }
}

fun main() {
    fun parseInput(input: List<String>): Dir {
        val root = Dir("/", null, mutableMapOf(), mutableListOf())
        var cwd = root
        input.forEach { line ->
            val parts = line.split(" ")
            when (parts[0]) {
                "$" -> when (parts[1]) {
                    "cd" -> {
                        when (parts[2]) {
                            "/" -> cwd = root
                            ".." -> cwd = cwd.parent!!
                            else -> {
                                cwd = cwd.children.getOrElse(parts[2]) {
                                    throw Exception("cd dir (" + parts[2] + ") before dir")
                                }
                            }
                        }
                    }

                    "ls" -> {
                    }

                    else -> throw Exception("invalid input")
                }

                "dir" -> {
                    if (!cwd.children.containsKey(parts[1])) {
                        cwd.children[parts[1]] = Dir(parts[1], cwd, mutableMapOf(), mutableListOf())
                    }
                }

                else -> { // file case
                    check(parts.count() == 2)
                    cwd.files.add(File(parts[1], parts[0].toInt()))
                }
            }
        }
        return root
    }

    fun partOne(input: List<String>) {
        println(parseInput(input).childrenAtMost(100000).sumOf { it.size() })
    }

    val max = 70000000 - 30000000
    fun partTwo(input: List<String>) {
        val dirs = parseInput(input)
        val totalSize = dirs.size()
        val diff = totalSize - max
        println(dirs.childrenAtLeast(diff).sortedBy { it.size() }.first().size())
    }

    println("partOne test")
    partOne(readInput("day7.test"))
    println()

    println("partOne")
    partOne(readInput("day7"))
    println()

    println("partTwo test")
    partTwo(readInput("day7.test"))
    println()

    println("partTwo")
    partTwo(readInput("day7"))
}