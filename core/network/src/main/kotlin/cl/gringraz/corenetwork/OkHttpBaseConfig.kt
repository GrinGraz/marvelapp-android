package cl.gringraz.corenetwork

import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion

object OkHttpBaseConfig {

    val baseOkhttpBuilder: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            connectionSpecs(listOf(createConnectionSpec()))
        }
    }

    private fun createConnectionSpec(): ConnectionSpec {
        return ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .build()
    }
}
