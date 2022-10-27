package cl.gringraz.corenetwork

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBaseConfig {

    val baseRetrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
        }
    }
}
