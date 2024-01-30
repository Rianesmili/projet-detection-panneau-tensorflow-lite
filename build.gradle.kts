buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.48.1")
        classpath ("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.6.0-1.0.2")
        classpath ("org.jlleitschuh.gradle:ktlint-gradle:11.5.1")
        classpath ("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.9.10-1.0.13")
    }
}

plugins {
    id ("com.android.application") version "8.1.2" apply false
    id ("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.0" apply false
    id ("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    id ("org.jetbrains.kotlin.jvm") version "1.8.10" apply false
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
}