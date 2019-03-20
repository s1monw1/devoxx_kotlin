package de.swirtz.devoxxuk.hof

// @formatter:off



fun <T> List<T>.doStuff(ops: List<T>.() -> Unit) {
    ops(this)
}














fun main(args: Array<String>) {
    val lst = listOf(1, 2, 3)
    lst.doStuff {
        println(size)
        println(first())
        println(last())
    }




    with(lst) {
        println(size)
        println(first())
        println(last())
    }

}


/**
 * Change to this
 *
 * Pull parameter out of parentheses to make it an "extension function type"
 *
 *
    fun <T> List<T>.doStuff(ops: List<T>.() -> Unit) {
    this.ops()
    }

    fun main(args: Array<String>) {
    listOf(1, 2, 3).doStuff {
    println(size)
    println(first())
    println(last())
    }
    }
 */

