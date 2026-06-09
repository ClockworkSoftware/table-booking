plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.serialization)
}

version = "$version"

dependencies {
    implementation(libs.kotlinx.serialization)
}