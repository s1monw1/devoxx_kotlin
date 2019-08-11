package de.swirtz.advancedkotlin.generics


import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass


// @formatter:off

class SomeArbitraryService() {
    val log = LoggerFactory.getLogger(SomeArbitraryService::class.java)

















    val log1 = logger(SomeArbitraryService::class)




    val log2 = logger2<SomeArbitraryService>()




    val log3 = logger3()
}

























//    (upper bound)
fun <T : Any> logger(clazz: KClass<T>): Logger = LoggerFactory.getLogger(clazz.java)
















inline fun <reified T : Any> logger2():Logger =
    LoggerFactory.getLogger(T::class.java)
















//type arguments can be ommitted (call-site) if
// the context makes it clear and the compiler can infer it
inline fun <reified T : Any> T.logger3(): Logger =
    LoggerFactory.getLogger(T::class.java)













//fun <T> String.toKotlinObject(): T {
//    val mapper = jacksonObjectMapper()
//    return mapper.readValue(this, T::class.java)
//}

















fun <T: Any> String.toKotlinObject2(clazz: KClass<T>): T {
    val mapper = jacksonObjectMapper()
    return mapper.readValue(this, clazz.java)
}




inline fun <reified T: Any> String.toKotlinObject3(): T {
    val mapper = jacksonObjectMapper()
    return mapper.readValue(this, T::class.java)
}
