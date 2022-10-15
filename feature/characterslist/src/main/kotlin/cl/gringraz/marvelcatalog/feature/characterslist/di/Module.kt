package cl.gringraz.marvelcatalog.feature.characterslist.di

import cl.gringraz.corenetwork.ApiConfig
import cl.gringraz.corenetwork.RetrofitClient
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.MarvelApi

val marvelApi by lazy {
    RetrofitClient(
        ApiConfig(
            baseUrl = "https://gateway.marvel.com:443/",
            authToken = "eeedcbaa3f39300836f1dac4e410e09d",
            timeout = 15L,
            isProd = false
        ), MarvelApi::class.java
    )
}