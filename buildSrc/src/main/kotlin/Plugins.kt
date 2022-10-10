object Plugins {
    data class Plugin(val name: String, val version: String)

    val detekt = Plugin("io.gitlab.arturbosch.detekt", "1.20.0")
    val ktLint = Plugin("org.jlleitschuh.gradle.ktlint", "10.2.1")
    val junit5 = Plugin("de.mannodermaus.android-junit5", "1.8.2.1")
}

