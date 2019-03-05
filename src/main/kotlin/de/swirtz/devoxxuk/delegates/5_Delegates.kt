package de.swirtz.devoxxuk.delegates

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class DelegationDemo {
    init {
        println("class initialized")
    }

    val lazyOne by lazy {
        println("lazy init")
        (0..1000).random()
    }

    fun usingLazyLocally(computation: () -> String) {
        val v by lazy { computation() }

    }

    var observedProp by Delegates.observable(10) { prop, old, new ->
        println("changed $prop from $old to $new")
    }


    var verifiedProp by Delegates.vetoable(5) { prop, old, new ->
        if (new > 10) {
            println("Veto!")
            false
        } else {
            println("No Veto")
            true
        }
    }


    val customDelegated by CustomDelegate(100)
}

fun main(args: Array<String>) {
    val delegation = DelegationDemo()
    println("created object")
    delegation.lazyOne
    delegation.lazyOne

    delegation.verifiedProp = 6
    delegation.verifiedProp = 11
    println(delegation.verifiedProp)
    delegation.observedProp = 20
    println(delegation.observedProp)

    println(delegation.customDelegated)
}

class CustomDelegate(var initValue: Int) : ReadWriteProperty<Any?, Int> {

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
        initValue = value * 10
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        println("$thisRef, thank you for delegating '${property.name}' to me!")
        return initValue
    }

}