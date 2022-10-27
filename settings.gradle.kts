pluginManagement {
    resolutionStrategy {
        eachPlugin {
            when (requested.id.namespace) {
                "com.android" -> useModule("com.android.tools.build:gradle:${requested.version}")
            }
        }
    }

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "Marvel Catalog"
include(
    ":app",
    ":core:network",
    ":feature:characterslist",
    ":feature:characterdetail",
    ":feature:common",
    ":feature:favoritecharacters"
)
