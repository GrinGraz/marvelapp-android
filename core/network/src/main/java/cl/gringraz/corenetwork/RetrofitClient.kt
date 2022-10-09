package cl.gringraz.corenetwork

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class RetrofitClient<T>(
    private val apiConfig: ApiConfig,
    private val clazz: Class<T>
) : ApiClient<T> {

    override val endpoints: T by lazy {
        val okHttpClient: OkHttpClient = okHttpBuilder().build()
        val retrofit: Retrofit = retrofitBuilder().client(okHttpClient).build()
        retrofit.create(clazz)
    }

    private fun retrofitBuilder(): Retrofit.Builder = RetrofitBaseConfig.baseRetrofitBuilder.apply {
        baseUrl(apiConfig.baseUrl)
    }

    private fun okHttpBuilder(): OkHttpClient.Builder = OkHttpBaseConfig.baseOkhttpBuilder.apply {
        readTimeout(apiConfig.timeout, TimeUnit.SECONDS)
        if (!apiConfig.isProd) addInterceptor(OkHttpBaseConfig.createHttpLoggingInterceptor())
    }
}
