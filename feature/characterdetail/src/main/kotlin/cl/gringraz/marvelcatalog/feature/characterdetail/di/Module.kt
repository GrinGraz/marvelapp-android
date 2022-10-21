package cl.gringraz.marvelcatalog.feature.characterdetail.di

import cl.gringraz.corenetwork.ApiClient
import cl.gringraz.corenetwork.RetrofitClient
import cl.gringraz.marvelcatalog.feature.characterdetail.BuildConfig
import cl.gringraz.marvelcatalog.feature.characterdetail.data.MarvelCharacterRepo
import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.MarvelCharacterRemote
import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote.MarvelCharacterApi
import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote.MarvelCharacterRemoteSource
import cl.gringraz.marvelcatalog.feature.characterdetail.domain.GetCharacterById
import cl.gringraz.marvelcatalog.feature.characterdetail.domain.MarvelCharacterRepository
import cl.gringraz.marvelcatalog.feature.characterdetail.presentation.MarvelCharacterDetailViewModel
import cl.gringraz.marvelcatalog.feature.common.data.MarvelApiConfig

private val apiConfig = MarvelApiConfig(
    baseUrl = BuildConfig.BASE_URL,
    publicKey = BuildConfig.PUBLIC_KEY,
    privateKey = BuildConfig.PRIVATE_KEY,
    isProd = BuildConfig.BUILD_TYPE == "release"
)

private val marvelApi: ApiClient<MarvelCharacterApi> by lazy {
    RetrofitClient(apiConfig, MarvelCharacterApi::class.java)
}

private val remoteSource: MarvelCharacterRemoteSource by lazy {
    MarvelCharacterRemote(marvelApi)
}

private val repo: MarvelCharacterRepository by lazy {
    MarvelCharacterRepo(remoteSource)
}

private val getCharacterByIdUseCase: GetCharacterById by lazy {
    GetCharacterById(repo::getMarvelCharacterById)
}

internal fun characterDetailViewModel(): MarvelCharacterDetailViewModel {
    return MarvelCharacterDetailViewModel(getCharacterByIdUseCase)
}
