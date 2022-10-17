package cl.gringraz.marvelcatalog.feature.characterslist.di
/*import cl.gringraz.corenetwork.ApiClient
import cl.gringraz.corenetwork.RetrofitClient
import cl.gringraz.marvelcatalog.feature.characterslist.BuildConfig
import cl.gringraz.marvelcatalog.feature.characterslist.CharactersViewModel
import cl.gringraz.marvelcatalog.feature.characterslist.data.MarvelCharactersRepo
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.MarvelCharactersRemote
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.MarvelApi
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.MarvelCharactersRemoteSource
import cl.gringraz.marvelcatalog.feature.characterslist.domain.usecase.GetMarvelCharacters
import cl.gringraz.marvelcatalog.feature.common.data.MarvelApiConfig
import cl.gringraz.marvelcatalog.feature.common.domain.characters.repository.MarvelCharactersRepository

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
    GetMarvelCharacters { repo.getMarvelCharacters() }
}*/
