package day4a

import readInput
import java.util.BitSet

fun Pair<Int, Int>.length(): Int {
    return this.second - this.first
}

fun main() {
    val count = readInput("day4").sumOf {
        val seqs = it.split(",")
        check(seqs.count() == 2)
        val a = seqs[0].split("-")
        check(a.count() == 2)
        val b = seqs[1].split("-")
        check(b.count() == 2)
        val bsa = BitSet(100)
        val bsb = BitSet(100)
        val aRange = Pair(a.first().toInt(), a.last().toInt() + 1)
        val bRange = Pair(b.first().toInt(), b.last().toInt() + 1)
        bsa.set(aRange.first, aRange.second, true)
        bsb.set(bRange.first, bRange.second, true)
        bsa.and(bsb)
        if (bsa.cardinality() == aRange.length() || bsa.cardinality() == bRange.length()) 1 else 0 as Int
    }

    println(count)
}