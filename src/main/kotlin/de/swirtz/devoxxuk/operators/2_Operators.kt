package de.swirtz.devoxxuk.operators

// @formatter:off

// Wraps two Ints
data class NumericHolder(val a: Int, val b: Int): Comparable<NumericHolder>{
    override fun compareTo(other: NumericHolder): Int {
        return (a + b).compareTo(other.a + other.b)
    }
}




















operator fun NumericHolder.plus(other: NumericHolder): NumericHolder {
    return NumericHolder(a + other.a, b + other.b)
}



val added = NumericHolder(1, 2) + NumericHolder(2, 3)



operator fun NumericHolder.minus(other: NumericHolder): NumericHolder {
    return NumericHolder(a - other.a, b - other.b)
}


val subtracted = NumericHolder(1, 2) - NumericHolder(2, 3)




operator fun NumericHolder.inc(): NumericHolder {
    return NumericHolder(a + 1, b + 1)
}

var holder = NumericHolder(3,4)
val incremented = holder++



operator fun NumericHolder.dec(): NumericHolder {
    return NumericHolder(a - 1, b - 1)
}

val dec = holder--



operator fun NumericHolder.get(i: Int): Int {
    return when (i) {
        0 -> a
        1 -> b
        else -> throw IndexOutOfBoundsException()
    }
}



val valuesSum = holder[0] + holder[1]


operator fun NumericHolder.contains(i: Int): Boolean {
    return a == i || b == i
}

val pred = 5 !in added





operator fun NumericHolder.invoke() {
    println(this)
}


val invoked = added()
//                      ^ that's how lambdas work




/**
 * Redundant since type is a `Comparable<NumericHolder> already
 */
operator fun NumericHolder.compareTo(other: NumericHolder): Int {
    return this.compareTo(other)
}


val great = added > subtracted
val greatEq = added >= subtracted
val less = added < subtracted
val lessEq = added <= subtracted

fun rangeExample(){

    // uses extension operator `Comparable<T>::rangeTo`
    val holderRange = subtracted..added



    for (e in holderRange) {
        println(e)
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