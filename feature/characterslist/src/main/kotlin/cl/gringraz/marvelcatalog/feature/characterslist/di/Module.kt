package cl.gringraz.marvelcatalog.feature.characterslist.di

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import cl.gringraz.corenetwork.ApiClient
import cl.gringraz.corenetwork.RetrofitClient
import cl.gringraz.marvelcatalog.feature.characterslist.BuildConfig
import cl.gringraz.marvelcatalog.feature.characterslist.data.MarvelCharactersRepo
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.MarvelCharactersRemote
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.MarvelApi
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.MarvelCharactersRemoteSource
import cl.gringraz.marvelcatalog.feature.characterslist.domain.usecase.GetMarvelCharacters
import cl.gringraz.marvelcatalog.feature.characterslist.domain.usecase.repository.MarvelCharactersRepository
import cl.gringraz.marvelcatalog.feature.characterslist.presentation.MarvelCharactersViewModel
import cl.gringraz.marvelcatalog.feature.common.data.MarvelApiConfig

private val apiConfig = MarvelApiConfig(
    baseUrl = BuildConfig.BASE_URL,
    publicKey = BuildConfig.PUBLIC_KEY,
    privateKey = BuildConfig.PRIVATE_KEY,
    isProd = BuildConfig.BUILD_TYPE == "release"
)

private val marvelApi: ApiClient<MarvelApi> by lazy {
    RetrofitClient(apiConfig, MarvelApi::class.java)
}

private val remoteSource: MarvelCharactersRemoteSource by lazy {
    MarvelCharactersRemote(marvelApi)
}

private val repo: MarvelCharactersRepository by lazy {
    MarvelCharactersRepo(remoteSource)
}

private val getCharactersUseCase: GetMarvelCharacters by lazy {
    GetMarvelCharacters(repo::getMarvelCharacters)
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
internal fun charactersViewModel(): MarvelCharactersViewModel {
    return MarvelCharactersViewModel(getCharactersUseCase)
}

val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        return MarvelCharactersViewModel(
            getCharactersUseCase
        ) as T
    }
}
