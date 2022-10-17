package cl.gringraz.corenetwork

import okhttp3.Interceptor

interface ApiConfig {
    val baseUrl: String
    val timeout: Long
    val isProd: Boolean
    fun getInterceptors(): List<Interceptor> = emptyList()
}
