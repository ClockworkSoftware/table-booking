// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

group = "org.clockwork.table-booking"
version = "1.0.0"

//java {
//    toolchain {
//        languageVersion = JavaLanguageVersion.of(21)
//    }
//}