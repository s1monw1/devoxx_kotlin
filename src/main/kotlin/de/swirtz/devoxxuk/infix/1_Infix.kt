package de.swirtz.devoxxuk.infix

// @formatter:off



// What is `to`?
val devoxxCities = mapOf(
    "uk" to "london",
    "belgium" to "antwerp",
    "ukraine" to "kyiv"
    //more
)
















// No magic keyword - Make it more explicit
val devoxxCities2 = mapOf(
    "uk".to("london"),
    "belgium".to("antwerp"),
    "ukraine".to("kyiv")
    //more
)






// What's really happening
val devoxxCities3 = mapOf(
    Pair("uk", "london"),
    Pair("belgium", "antwerp"),
    Pair("ukraine", "kyiv")
    //more
)










// A custom function
fun <T> List<T>.combineWith(other: List<T>): List<T> {
    // naive implementation
    val combined = mutableListOf<T>()
    combined.addAll(this)
    combined.addAll(other)
    return combined
}


val all =
    listOf(1, 2, 3).combineWith(listOf(5, 6))





// Better approach to combine lists
infix fun <T> List<T>.improvedCombineWith(other: List<T>): List<T> {
    return this + other
}