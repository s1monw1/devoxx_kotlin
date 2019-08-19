package de.swirtz.advancedkotlin.infix

// @formatter:off



// What is `to`?
val droidconCities = mapOf(
    "uk" to "london",
    "usa" to "new york",
    "germany" to "berlin"
    //more
)















// No magic keyword - Make it more explicit
val droidconCities2 = mapOf(
    "uk".to("london"),
    "usa".to("new york"),
    "germany".to("berlin")
    //more
)






// What's really happening
val devoxxCities3 = mapOf(
    Pair("uk", "london"),
    Pair("usa", "new york"),
    Pair("germany", "berlin")
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