plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_1_7
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(project(Deps.core.network))
    implementation(Deps.squareup.okhttp)
    implementation(Deps.squareup.loggingInterceptor)
    implementation(Deps.google.gson)
    implementation(Deps.thirdParty.arrowCore)
    implementation(Deps.androidx.annotations)
    testImplementation(kotlin("test"))
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.20.0")
}
