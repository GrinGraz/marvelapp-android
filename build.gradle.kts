// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.0")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.8.2.1")
    }
}

plugins {
    id(Plugins.detekt.name) version Plugins.detekt.version
    id(Plugins.ktLint.name) version Plugins.ktLint.version
}

allprojects {
    Keys.initialize(File("auth.properties"))
    repositories.applyDefault()

    val javaVersion = JavaVersion.VERSION_11.toString()

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = javaVersion
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion

            // Enable experimental coroutines APIs, including collectAsState()
            freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        }
    }

    tasks.withType<JavaCompile>().all {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    apply {
        plugin(Plugins.detekt.name)
        plugin(Plugins.ktLint.name)
    }

    detekt {
        toolVersion = "1.20.0"
        config = rootProject.files("$rootDir/.detekt/config.yml")
        reports {
            html {
                required.set(true)
                outputLocation.set(file("build/reports/detekt/report.html"))
            }
        }
    }

    ktlint {
        debug.set(false)
        version.set("0.45.2")
        verbose.set(true)
        android.set(false)
        outputToConsole.set(true)
        ignoreFailures.set(false)
        enableExperimentalRules.set(true)
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }
}
