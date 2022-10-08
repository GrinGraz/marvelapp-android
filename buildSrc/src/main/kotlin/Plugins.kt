object Plugins {
    data class Plugin(val name: String, val version: String)

    val detekt = Plugin("io.gitlab.arturbosch.detekt", "1.21.0")
    val ktLint = Plugin("org.jlleitschuh.gradle.ktlint", "10.2.1")
}

