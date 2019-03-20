package de.swirtz.devoxxuk.operators

// @formatter:off

//Operators and conventions

data class NumericHolder(val a: Int, val b: Int) :Comparable<NumericHolder>{
    override fun compareTo(other: NumericHolder): Int {
        TODO("not implemented")
    }
}




















operator fun NumericHolder.plus(other: NumericHolder): NumericHolder {
    return NumericHolder(a + other.a, b + other.b)
}

var plus = NumericHolder(1, 2) + NumericHolder(2, 3)



operator fun NumericHolder.minus(other: NumericHolder): NumericHolder {
    return NumericHolder(a - other.a, b - other.b)
}


val minus = NumericHolder(1, 2) - NumericHolder(2, 3)




operator fun NumericHolder.inc(): NumericHolder {
    return NumericHolder(a + 1, b + 1)
}

val incremented = plus++



operator fun NumericHolder.dec(): NumericHolder {
    return NumericHolder(a - 1, b - 1)
}

val dec = plus--



operator fun NumericHolder.get(i: Int): Int {
    return when (i) {
        0 -> a
        1 -> b
        else -> throw IndexOutOfBoundsException()
    }
}

val firstPart = NumericHolder(1, 2)[1]


operator fun NumericHolder.contains(i: Int): Boolean {
    return a == i || b == i
}

val pred = 5 !in plus





operator fun NumericHolder.invoke() {
    println(this)
}


val invoked = plus()
//                      ^ that's how lambdas work





operator fun NumericHolder.compareTo(other: NumericHolder): Int {
    return (a + b).compareTo(other.a + other.b)
}


val great = plus > minus
val greatEq = plus >= minus
val less = plus < minus
val lessEq = plus <= minus


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