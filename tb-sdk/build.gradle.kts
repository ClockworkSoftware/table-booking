plugins {
    kotlin("jvm") version "2.2.21"
}

group = "org.clockwork.table-booking"
version = "$version"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}