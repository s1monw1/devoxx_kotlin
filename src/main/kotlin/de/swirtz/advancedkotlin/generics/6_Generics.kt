package de.swirtz.advancedkotlin.generics

// @formatter:off

//java has scary wildcards: List<? super String> or
//Collection<? extends Object>

//used to specify variance, e.g. List<String> is a subtype of List<Object>
//you can't just assign an object of `List<String>`
//to a variable of type `List<Object>`

//Effective Java by Joshua Bloch: Producer-Extends, Consumer-Super

//Kotlin has declaration-site variance (Java does not, only use-site)
//sometimes it's clear that specific types are only gonna be used
//as producers or consumers of a generic type T

interface SourceOf<out T> {
    fun get(): T
}









val source = object : SourceOf<Int> {
        override fun get() = 1
    }

val numberSource: SourceOf<Number> = source

val fromSource = numberSource.get()
//we get a number, we don't care which subtype exactly


fun takeNumberSource(src: SourceOf<Number>) = src.get()


val invoked = takeNumberSource(source)


//SourceOf is "covariant in the type parameter T"




interface DestinationOf<in T> {
    fun add(t: T)
}


val dest = object : DestinationOf<Number> {
    override fun add(t: Number) {
      //we always expect a number, supertypes are fine!
    }

}

val intDest: DestinationOf<Int> = dest

val added = intDest.add(1)

//DestinationOf is "contravariant in the type paramter T"




fun main() {

    //use-site variance, type projection
    //we say that from isn't a regular Array but a projected one
    fun copy(from: Array<out Any>, to: Array<Any>) {
        //from[2] = "EVIL_STRING"
        assert(from.size == to.size)
        from.forEachIndexed { i, e ->
            to[i] = e
        }
    }

    //Array<T> is invariant in T
    val ints: Array<Int> = arrayOf(1, 2, 3)
    val any = Array<Any>(3) { "something" }
    copy(ints, any)










    //star projection: we don't have information about generic type
    //projected to List<MutableList<out Any?>>
    val lists: List<MutableList<*>> = listOf()
    //Any? is supertype of everything else,
    // so it's safe to get elements of this type
    // from star-projected types
    val fromList = lists[0][1]
    //lists[0][1] = 1

    //we use star projection when we don't care about the actual
    //type argument

    fun List<*>.printContent(){
        //we don't use functions that rely on T
        forEach { println(it) }
    }

    listOf(1,2,3).printContent()
    listOf("1","2","3").printContent()
}