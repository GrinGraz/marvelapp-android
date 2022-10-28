plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
}

android {
    namespace = "cl.gringraz.marvelcatalog"
    compileSdk = AppConfig.compileSdkVersion

    signingConfigs {
        create("release") {
            storeFile = file("../upload-keystore.jks")
            storePassword = Keys.keystorePass
            keyAlias = Keys.keystoreAlias
            keyPassword = Keys.keystorePass
        }
    }

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
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
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
    implementation(project(Deps.features.characterList))
    implementation(project(Deps.features.characterDetail))
    implementation(project(Deps.features.favoriteCharacters))

    implementation(platform(Deps.google.firebaseBom))
    implementation(Deps.androidx.coreKtx)
    implementation(Deps.androidx.appCompat)
    implementation(Deps.androidx.navigationUi)
    implementation(Deps.androidx.navigationFragment)
    implementation(Deps.google.material)
    implementation(Deps.google.firebase.crashlytics)
    implementation(Deps.google.firebase.analytics)
    implementation(Deps.google.firebase.performance)
    // implementation(Deps.thirdParty.flagboard)

    testImplementation(Deps.test.junit4)
    androidTestImplementation(Deps.androidxTest.junitExtensions)
    androidTestImplementation(Deps.androidxTest.espressoCore)

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.20.0")
}
