package de.swirtz.devoxxuk.contracts

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// @formatter:off

fun process(data: List<String>?) {
    if (!data.isNullOrEmpty()) {
        //println(data.size)











        val someElement: String
        data.let {
            //someElement = it.last()
        }


    }

}




fun <T> Collection<T>?.isNullOrEmpty(): Boolean {

    return this == null || this.isEmpty()
}








inline fun <T, R> T.let(block: (T) -> R): R {

    return block(this)
}