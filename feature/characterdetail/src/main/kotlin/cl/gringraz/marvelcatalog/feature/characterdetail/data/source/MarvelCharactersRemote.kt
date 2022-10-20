package cl.gringraz.marvelcatalog.feature.characterdetail.data.source

import arrow.core.Either
import cl.gringraz.corenetwork.ApiClient
import cl.gringraz.corenetwork.RemoteError
import cl.gringraz.corenetwork.apiCall
import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote.MarvelApi
import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote.MarvelCharactersRemoteSource
import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote.model.MarvelCharactersResponseModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.CharactersRequestQueryModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MarvelCharactersRemote(
    private val marvelApi: ApiClient<MarvelApi>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MarvelCharactersRemoteSource {

    override suspend fun getMarvelCharacterById(
        id: String
    ): Either<RemoteError, MarvelCharactersResponseModel?> {
        return apiCall(dispatcher) { marvelApi.endpoints.getMarvelCharacterById(id = id) }
            .map { marvelCharactersResponse ->
                marvelCharactersResponse.body()
            }
    }
}
