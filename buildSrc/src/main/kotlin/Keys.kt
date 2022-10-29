import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*

object Keys {
    var publicKey: String = ""
    var privateKey: String = ""
    var jitPackToken: String = ""
    var keystorePass: String = ""
    var keystoreAlias: String = ""

    fun initialize(file: File) {
        try {
            val fis = FileInputStream(file)
            val authProperties = Properties()
            authProperties.load(fis)
            publicKey = authProperties["publicKey"].toString()
            privateKey = authProperties["privateKey"].toString()
            jitPackToken = authProperties["jitpackToken"].toString()
            keystorePass = authProperties["keystorePass"].toString()
            keystoreAlias = authProperties["keystoreAlias"].toString()
        } catch (ex: FileNotFoundException) {
            publicKey = System.getenv("publicKey")
            privateKey = System.getenv("privateKey")
            jitPackToken = System.getenv("jitpackToken")
            keystorePass = System.getenv("releaseKeystorePass")
            keystoreAlias = System.getenv("releaseKeystoreAlias")
        }
    }
}
