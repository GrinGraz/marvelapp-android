import org.gradle.api.artifacts.dsl.RepositoryHandler
import java.net.URI

fun RepositoryHandler.applyDefault() {
    google()
    mavenCentral()
    maven {
        url = URI("https://jitpack.io")
        credentials {
            username = Keys.jitPackToken
        }
    }
}