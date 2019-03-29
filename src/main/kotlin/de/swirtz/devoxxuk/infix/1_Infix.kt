package de.swirtz.devoxxuk.infix

// @formatter:off



//What is `to`?
val devoxxCities = mapOf(
    "uk" to "london",
    "belgium" to "antwerp",
    "ukraine" to "kyiv"
    //more
)
















//More explicit
val devoxxCities2 = mapOf(
    "uk".to("london"),
    "belgium".to("antwerp"),
    "ukraine".to("kyiv")
    //more
)







val devoxxCities3 = mapOf(
    Pair("uk", "london"),
    Pair("belgium", "antwerp"),
    Pair("ukraine", "kyiv")
    //more
)











//Custom function

infix fun <T> List<T>.combineWith(other: List<T>): List<T> {
    //naive implementation, improve later!
    val combined = mutableListOf<T>()
    combined.addAll(this)
    combined.addAll(other)
    return combined
}


val all = listOf(1, 2, 3, 4, 5).combineWith(listOf(5, 6, 7, 8))





infix fun <T> List<T>.combineWithImproved(other: List<T>): List<T> {
    return this + other
}