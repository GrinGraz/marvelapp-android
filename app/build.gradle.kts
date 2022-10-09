plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":feature:characterslist"))
    implementation(project(":feature:characterdetail"))
    // List experiment
    Deps.androidX.forEach { dep -> implementation(dep) }
    Deps.third.forEach { dep -> implementation(dep) }
    Deps.coroutine.forEach { dep -> implementation(dep) }

    implementation(Deps.google.material)

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
