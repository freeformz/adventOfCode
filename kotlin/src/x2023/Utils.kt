package x2023

import java.io.File

fun read2023Input(name: String) = File("kotlin/src/x2023/input", "${name}.input")
    .readLines()