plugins {
    id("com.android.library")
    // id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id(Plugins.junit5.name)
}

android {
    namespace = "cl.gringraz.marvelcatalog.feature.characterdetail"
    compileSdk = 33

    defaultConfig {
        // Keep this to develop and deploy APK
        // applicationId = "cl.gringraz.feature.marvelcatalog.characterdetail"
        // versionCode = 1
        // versionName = "1.0"
        minSdk = 28
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "PUBLIC_KEY", "\"${Keys.publicKey}\"")
            buildConfigField("String", "PRIVATE_KEY", "\"${Keys.privateKey}\"")
            buildConfigField("String", "BASE_URL", "\"https://gateway.marvel.com/\"")
        }
        debug {
            buildConfigField("String", "PUBLIC_KEY", "\"${Keys.publicKey}\"")
            buildConfigField("String", "PRIVATE_KEY", "\"${Keys.privateKey}\"")
            buildConfigField("String", "BASE_URL", "\"https://gateway.marvel.com/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
    packagingOptions {
        resources.pickFirsts.apply {
            add("MANIFEST.MF")
        }
    }
}

dependencies {
    implementation(project(Deps.core.network))
    implementation(project(Deps.features.common))
    implementation(Deps.google.gson)
    Deps.androidX.forEach { dep -> implementation(dep) }
    Deps.third.forEach { dep -> implementation(dep) }
    Deps.coroutine.forEach { dep -> implementation(dep) }
    Deps.square.forEach { dep -> implementation(dep) }

    testImplementation(Deps.test.api)
    testImplementation(Deps.test.junit5)
    testImplementation(Deps.test.kotlinTests)
    testImplementation(Deps.test.mockkAgent)
    testImplementation(Deps.test.mockkAndroid)
    testImplementation(Deps.coroutines.test)
    testImplementation(Deps.androidxTest.junitExtensions)
    testRuntimeOnly(Deps.test.jupiterEngine)
    androidTestUtil(Deps.androidxTest.testOrchestrator)
    androidTestImplementation(Deps.androidxTest.testCore)
    androidTestImplementation(Deps.androidxTest.testRunner)
    androidTestImplementation(Deps.test.mockkAndroid)
    androidTestImplementation(Deps.test.mockkAgent)
    debugImplementation(Deps.androidxTest.fragmentTest)
    androidTestImplementation(Deps.androidxTest.junitKtx)
    androidTestImplementation(Deps.test.barista) { exclude("org.jetbrains.kotlin") }

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.20.0")
}
