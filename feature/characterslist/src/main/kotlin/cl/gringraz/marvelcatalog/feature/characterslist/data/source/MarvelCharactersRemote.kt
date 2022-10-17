package cl.gringraz.marvelcatalog.feature.characterslist.data.source

import arrow.core.Either
import cl.gringraz.corenetwork.ApiClient
import cl.gringraz.corenetwork.RemoteError
import cl.gringraz.corenetwork.apiCall
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.MarvelApi
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.MarvelCharactersRemoteSource
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.model.MarvelCharactersResponseModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MarvelCharactersRemote(
    private val marvelApi: ApiClient<MarvelApi>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MarvelCharactersRemoteSource {

    override suspend fun getMarvelCharacters(): Either<RemoteError, MarvelCharactersResponseModel?> {
        return apiCall(dispatcher) { marvelApi.endpoints.getMarvelCharacters() }
            .map { marvelCharactersResponse ->
                marvelCharactersResponse.body()
            }
    }
}
