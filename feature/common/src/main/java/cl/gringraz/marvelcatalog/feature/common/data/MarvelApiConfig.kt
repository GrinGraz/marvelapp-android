package cl.gringraz.marvelcatalog.feature.common.data

import cl.gringraz.corenetwork.ApiConfig
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

data class MarvelApiConfig(
    override val baseUrl: String,
    val publicKey: String,
    val privateKey: String,
    override val timeout: Long = 15L,
    override val isProd: Boolean = true,
) : ApiConfig {

    override fun getInterceptors(): List<Interceptor> = buildList {
        add(authInterceptor)
        if (!isProd) add(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    }

    private val authInterceptor: Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val timeStamp = Date().toString()
        val hash = md5(timeStamp + privateKey + publicKey)
        val interceptedUrl = originalUrl.newBuilder()
            .addQueryParameter("apikey", publicKey)
            .addQueryParameter("ts", timeStamp)
            .addQueryParameter("hash", hash)
            .build()
        val interceptedRequest = originalRequest.newBuilder()
            .url(interceptedUrl)
            .build()
        chain.proceed(interceptedRequest)
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}
