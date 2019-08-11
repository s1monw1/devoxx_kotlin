package de.swirtz.advancedkotlin.hof

// @formatter:off



fun <T> List<T>.doStuff(ops: (List<T>) -> Unit) {
    ops(this)
}






















fun main() {
    val lst = listOf(1, 2, 3)
    lst.doStuff {
        println(it.size)
        println(it.first())
        println(it.last())
    }




    with(lst) {
        println(size)
        println(first())
        println(last())
    }

}
