plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "cl.gringraz.marvelcatalog.feature.characterslist"
    compileSdk = 33

    defaultConfig {
        // applicationId = "cl.gringraz.marvelcatalog.feature.characterslist"
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
}

dependencies {
    implementation(project(":core:network"))

    Deps.androidX.forEach { dep -> implementation(dep) }
    Deps.third.forEach { dep -> implementation(dep) }
    Deps.coroutine.forEach { dep -> implementation(dep) }

    testImplementation(Deps.test.api)
    testImplementation(Deps.test.params)
    testImplementation(Deps.test.kotlinTests)
    testImplementation(Deps.test.mockkAgent)
    testImplementation(Deps.test.mockkAndroid)
    testImplementation(Deps.coroutines.test)
    testImplementation(Deps.androidxTest.testRules)
    testImplementation(Deps.androidxTest.junitExtensions)
    testRuntimeOnly(Deps.test.jupiterEngine)

    androidTestUtil(Deps.androidxTest.testOrchestrator)
    androidTestImplementation(Deps.androidxTest.testCore)
    androidTestImplementation(Deps.androidxTest.testRunner)
    androidTestImplementation(Deps.androidxTest.espressoCore)
    androidTestImplementation(Deps.androidxTest.espressoContrib)
    androidTestImplementation(Deps.androidxTest.espressoIntents)
    androidTestImplementation(Deps.test.mockkAndroid)
    androidTestImplementation(Deps.test.mockkAgent)

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.20.0")
}
