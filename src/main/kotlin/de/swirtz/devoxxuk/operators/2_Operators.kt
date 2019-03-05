package de.swirtz.devoxxuk.operators

//Operators and conventions

data class NumericHolder(val a: Int, val b: Int) : Comparable<NumericHolder> {
    operator fun plus(other: NumericHolder): NumericHolder {
        return NumericHolder(a + other.a, b + other.b)
    }

    operator fun minus(other: NumericHolder): NumericHolder {
        return NumericHolder(a - other.a, b - other.b)
    }

    operator fun inc(): NumericHolder {
        return NumericHolder(a + 1, b + 1)
    }

    operator fun dec(): NumericHolder {
        return NumericHolder(a - 1, b - 1)
    }

    operator fun get(i: Int): Int {
        return when (i) {
            0 -> a
            1 -> b
            else -> throw IndexOutOfBoundsException()
        }
    }

    operator fun contains(i: Int): Boolean {
        return a == i || b == i
    }

    operator fun invoke() {
        println(this)
    }

    override operator fun compareTo(other: NumericHolder): Int {
        return (a + b).compareTo(other.a + other.b)
    }

}

operator fun ClosedRange<NumericHolder>.iterator() =
    object : Iterator<NumericHolder> {
        var current = start
        override fun hasNext(): Boolean {
            return current < endInclusive
        }

        override fun next(): NumericHolder {
            return current++
        }

    }

fun main(args: Array<String>) {

    var plus = NumericHolder(1, 2) + NumericHolder(2, 3)
    val minus = NumericHolder(1, 2) - NumericHolder(2, 3)
    val incremented = plus++
    val dec = plus--
    println(plus)
    println(plus[1])
    println(5 !in plus)
    //invoke
    plus()
    plus == minus
    println(plus > minus)
    println(plus >= minus)
    println(plus <= minus)

    val holderRange = minus..plus
    println("printing range")
    for (e in holderRange) {
        println(e)
    }

    //destructuring convention: componentX

    val predicate = SpecialStringPredicate()
    predicate("string123")
}

class SpecialStringPredicate : (String) -> Boolean {
    override fun invoke(p1: String): Boolean {
        return p1.length == 5
    }

}