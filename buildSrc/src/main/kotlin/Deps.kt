@file:Suppress("ClassName")

object Deps {
    object androidx {
        const val appCompat          = "androidx.appcompat:appcompat:1.5.1"
        const val constraintLayout   = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val recyclerView       = "androidx.recyclerview:recyclerview:1.2.1"
        const val viewModel          = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:2.5.2"
        const val navigationUi       = "androidx.navigation:navigation-ui-ktx:2.5.2"

        const val junitExtensions   = "androidx.test.ext:junit:1.1.3"
        const val testRunner        = "androidx.test:runner:1.4.0"
        const val testCore          = "androidx.test:core:1.4.0"
        const val testRules         = "androidx.test:rules:1.4.0"
        const val testOrchestrator  = "androidx.test:orchestrator:1.4.0"
        const val espressoIntents   = "androidx.test.espresso:espresso-intents:3.3.0"
        const val espressoContrib   = "androidx.test.espresso:espresso-contrib:3.3.0"
        const val espressoCore      = "androidx.test.espresso:espresso-core:3.4.0"
    }

    object test {
        const val plugin         = "de.mannodermaus.gradle.plugins:android-junit5:1.7.1.1"
        const val api            = "org.junit.jupiter:junit-jupiter-api:5.7.1"
        const val params         = "org.junit.jupiter:junit-jupiter-params:5.7.1"
        const val jupiterEngine = "org.junit.jupiter:junit-jupiter-engine:5.7.1"
        const val runner        = "org.junit.platform:junit-platform-runner:1.7.1"
        const val kotlinTests   = "org.jetbrains.kotlin:kotlin-test-junit:1.6.20"
        const val mockk          = "io.mockk:mockk:1.10.6"
        const val mockkAndroid  = "io.mockk:mockk-android:1.10.6"
    }

    object google {
        const val material = "com.google.android.material:material:1.6.1"
    }

    object squareup {
        const val retrofit           = "com.squareup.retrofit2:retrofit:2.9.0"
        const val gsonConverter      = "com.squareup.retrofit2:converter-gson:2.9.0"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.3"
        const val okhttp             = "com.squareup.okhttp3:okhttp:4.9.3"
    }

    object thirdParty {
        const val coil    = "io.coil-kt:coil:2.2.2"
        const val shimmer = "dev.volo.shimmer:shimmer:0.6.0-alpha01" // facebook shimmer build from main branch
        const val timber  = "com.jakewharton.timber:timber:5.0.1"
    }

    object coroutines {
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
        const val test    = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
    }

    // List experiment
    val android = listOf(
        "androidx.appcompat:appcompat:1.5.1",
        "androidx.constraintlayout:constraintlayout:2.1.4",
        "androidx.recyclerview:recyclerview:1.2.1",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1",
        "androidx.navigation:navigation-fragment-ktx:2.5.2",
        "androidx.navigation:navigation-ui-ktx:2.5.2",
        "androidx.test.ext:junit:1.1.3",
        "androidx.test:runner:1.4.0",
        "androidx.test:core:1.4.0",
        "androidx.test:rules:1.4.0",
        "androidx.test:orchestrator:1.4.0",
        "androidx.test.espresso:espresso-intents:3.3.0",
        "androidx.test.espresso:espresso-contrib:3.3.0",
        "androidx.test.espresso:espresso-core:3.4.0",
    )

    val testing = listOf(
        "de.mannodermaus.gradle.plugins:android-junit5:1.7.1.1",
        "org.junit.jupiter:junit-jupiter-api:5.7.1",
        "org.junit.jupiter:junit-jupiter-params:5.7.1",
        "org.junit.jupiter:junit-jupiter-engine:5.7.1",
        "org.junit.platform:junit-platform-runner:1.7.1",
        "org.jetbrains.kotlin:kotlin-test-junit:1.6.20",
        "io.mockk:mockk:1.10.6",
        "io.mockk:mockk-android:1.10.6",
    )

    val square = listOf(
        "com.squareup.retrofit2:retrofit:2.9.0",
        "com.squareup.retrofit2:converter-gson:2.9.0",
        "com.squareup.okhttp3:logging-interceptor:4.10.0",
        "com.squareup.okhttp3:okhttp:4.10.0",
    )

    val third = listOf(
        "io.coil-kt:coil:2.2.2",
        "dev.volo.shimmer:shimmer:0.6.0-alpha01", // facebook shimmer build from main branch
        "com.jakewharton.timber:timber:5.0.1",
    )

    val coroutine = listOf(
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4",
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4",
    )
}