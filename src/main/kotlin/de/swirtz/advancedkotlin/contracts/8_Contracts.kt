package de.swirtz.advancedkotlin.contracts

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// @formatter:off

fun process(data: List<String>?) {
    if (!data.isNullOrEmpty()) {
        println(data.size)











        val someElement: String
        data.let {
            someElement = it.last()
        }


    }

}




@UseExperimental(ExperimentalContracts::class)
fun <T> Collection<T>?.isNullOrEmpty(): Boolean {
    contract {
        returns(false) implies (this@isNullOrEmpty != null)
    }
    return this == null || this.isEmpty()
}








@UseExperimental(ExperimentalContracts::class)
inline fun <T, R> T.let(block: (T) -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block(this)
}