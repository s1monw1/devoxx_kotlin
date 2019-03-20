package de.swirtz.devoxxuk.inlineclass

// @formatter:off

import java.math.BigDecimal
import java.math.RoundingMode


fun authenticate(user: String, password: String) {}



fun useAuthenticate(){
    authenticate("paul", "123456")
}








inline class Name(val name: String)
inline class Password(val pw: String)



fun authenticate(user: Name, password: Password) {}



fun useAuthenticateInline(){
    //more type safety
    authenticate(Name("paul"), Password("123456"))
}


/**
 * parses number into BigDecimal with a scale of 2
 */
fun parseNumber(string: String): BigDecimal {
    return string.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
}



inline class ParsedNumber(val original: String) {
    val parsed: BigDecimal
        get() = original.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
}

fun parseNumber2(string: String): ParsedNumber {
    return ParsedNumber(string)
}

fun main() {
    println(parseNumber2("100.12212").parsed)
    println(parseNumber2("100.145").parsed)
    println(parseNumber2("100.0").parsed)
}