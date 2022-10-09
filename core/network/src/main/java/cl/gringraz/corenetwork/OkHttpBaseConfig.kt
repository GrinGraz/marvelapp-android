package cl.gringraz.corenetwork

import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpBaseConfig {

    val baseOkhttpBuilder: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            connectionSpecs(listOf(createConnectionSpec()))
        }
    }

    fun createHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private fun createConnectionSpec(): ConnectionSpec {
        return ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .build()
    }
}
