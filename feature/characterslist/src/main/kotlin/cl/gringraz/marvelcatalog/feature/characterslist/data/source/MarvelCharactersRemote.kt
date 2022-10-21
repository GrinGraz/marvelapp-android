package cl.gringraz.marvelcatalog.feature.characterslist.data.source

import arrow.core.Either
import cl.gringraz.corenetwork.ApiClient
import cl.gringraz.corenetwork.RemoteError
import cl.gringraz.corenetwork.apiCall
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.MarvelApi
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.MarvelCharactersRemoteSource
import cl.gringraz.marvelcatalog.feature.common.data.MarvelCharactersResponseModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.CharactersRequestQueryModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MarvelCharactersRemote(
    private val marvelApi: ApiClient<MarvelApi>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MarvelCharactersRemoteSource {

    override suspend fun getMarvelCharacters(
        requestModel: CharactersRequestQueryModel?
    ): Either<RemoteError, MarvelCharactersResponseModel?> {
        val queryMap = getQueryParamsOrEmpty(requestModel)
        return apiCall(dispatcher) { marvelApi.endpoints.getMarvelCharacters(queryStrings = queryMap) }
            .map { marvelCharactersResponse ->
                marvelCharactersResponse.body()
            }
    }

    private fun getQueryParamsOrEmpty(requestModel: CharactersRequestQueryModel?): Map<String, String> {
        if (requestModel == null) return emptyMap()
        return buildMap {
            with(requestModel) {
                nameStartsWith?.let { put("nameStartsWith", it) }
                limit?.let { put("limit", it.toString()) }
                offset?.let { put("offset", it.toString()) }
            }
        }
    }
}
