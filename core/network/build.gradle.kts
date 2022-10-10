plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    Deps.square.forEach { dep -> implementation(dep) }
    Deps.coroutine.forEach { dep -> implementation(dep) }
    implementation(Deps.thirdParty.arrowCore)

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.20.0")
}
