plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    Deps.square.forEach { dep -> implementation(dep) }
    Deps.coroutine.forEach { dep -> implementation(dep) }
    implementation(Deps.thirdParty.arrowCore)

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.20.0")
}
