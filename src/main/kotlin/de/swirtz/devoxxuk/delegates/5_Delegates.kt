package de.swirtz.devoxxuk.delegates

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Delegated Properties: delegating accessor logic to some helper
 */
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
        println("changed ${prop.name} from $old to $new")
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


    var customDelegated by ModifiedDelegate(100) { it * 10 }
    var customDelegated2 by modified(100) { it * 10 }

}

fun main(args: Array<String>) {
    val delegation = DelegationDemo()
    println("created object")
    delegation.lazyOne
    delegation.lazyOne

    delegation.observedProp = 20
    println(delegation.observedProp)

    delegation.verifiedProp = 6
    delegation.verifiedProp = 11
    println(delegation.verifiedProp)


    delegation.customDelegated = 2
    println(delegation.customDelegated)

}

fun <T> modified(initValue: T, modifier: (T) -> T) = ModifiedDelegate(initValue, modifier)

class ModifiedDelegate<T>(val initValue: T, val modifier: (T) -> T) : ReadWriteProperty<Any?, T> {

    private var _current = modifier(initValue)

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
        _current = modifier(value)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        println("$thisRef, thank you for delegating '${property.name}' to me!")
        return _current
    }

}