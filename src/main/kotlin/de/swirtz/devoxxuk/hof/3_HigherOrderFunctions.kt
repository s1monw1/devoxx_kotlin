package de.swirtz.devoxxuk.hof

import java.io.FileInputStream
import java.util.concurrent.locks.ReentrantLock

typealias IntMapper = (Int) -> Int

//Presentation:
// 1) Show HOF without inline, add inline and describe it
// 2) Demonstrate illegal use of inline with assigning function and introduce noinline
// 3) Show extension HOF
// Why noinline? inline has restrictions:
//
//
//
// you can inline when param is called or passed to other !inline! function,
//
// you cannot inline when function is being assigned to variable e.g.
//      reason: assigned function has to be part of an object
// you should not inline long functions, think titled byte code

fun calculate(param: Int, operation: IntMapper): Int {
    return operation(param)
}


inline fun calculateInline(param: Int, operation: IntMapper): Int {
    return operation(param)
}


fun calculateNoInline(param: Int, operation: IntMapper): Int {
    val o = operation
    return o(param)
}

fun Int.calculateSelf(operation: IntMapper) = operation(this)

fun controlFlow() {
    listOf(1, 2, 3).forEach {
        if (it % 2 == 0) return
    }

    println("done")
}

// What will be printed by controlFlow?
//
//
// nothing, non-local return
//
// Fix with labels, like break in for loop


fun controlFlowLocal() {
    listOf(1, 2, 3).forEach someLoop@{
        if (it % 2 == 0) return@someLoop
    }
    println("done")

    //alternative with anonymous function
    listOf(1, 2, 3).forEach(fun(x: Int) {
        if (x % 2 == 0) return
    })
    println("done")


    //Rule: return returns from the closest function declared with the fun keyword
    calculate(5) {
        return@calculate it * 5
    }
}


fun stdLibExamples() {
    val resultOfSyncBlock =
        synchronized(ReentrantLock()) {
            println("do stuff")
            "return_value"
        }

    FileInputStream("path").use {
        println("do other stuff")
    }

    listOf(1, 2, 3, 4).forEach { println(it) }

}

fun main(args: Array<String>) {
    calculate(5) { it * 10 }

    5.calculateSelf { it * 20 }

    controlFlow()
    //controlFlowLocal()

}