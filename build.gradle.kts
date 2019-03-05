import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = plugins.getPlugin(KotlinPluginWrapper::class.java).kotlinPluginVersion
val kotlinxCoroutinesVersion = "1.1.1"

plugins {
    kotlin("jvm") version "1.3.21"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    compile(kotlin("stdlib-jdk8", kotlinVersion))
    compile(kotlin("reflect", kotlinVersion))
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
    compile("org.slf4j:slf4j-api:1.7.14")
    compile("ch.qos.logback:logback-classic:1.1.3")
    testCompile(kotlin("test-junit", kotlinVersion))
    testCompile("junit:junit:4.11")
}

repositories {
    mavenCentral()
    jcenter()
}
