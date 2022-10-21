package cl.gringraz.marvelcatalog.feature.characterdetail.data.source

import arrow.core.Either
import cl.gringraz.corenetwork.ApiClient
import cl.gringraz.corenetwork.RemoteError
import cl.gringraz.corenetwork.apiCall
import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote.MarvelCharacterApi
import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote.MarvelCharacterRemoteSource
import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote.model.MarvelCharactersResponseModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MarvelCharacterRemote(
    private val marvelApi: ApiClient<MarvelCharacterApi>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MarvelCharacterRemoteSource {

    override suspend fun getMarvelCharacterById(
        id: String
    ): Either<RemoteError, MarvelCharactersResponseModel?> {
        return apiCall(dispatcher) { marvelApi.endpoints.getMarvelCharacterById(id = id) }
            .map { marvelCharactersResponse ->
                marvelCharactersResponse.body()
            }
    }
}
