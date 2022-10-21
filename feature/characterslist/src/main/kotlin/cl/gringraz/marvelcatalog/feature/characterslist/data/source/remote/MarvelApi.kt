package cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote

import cl.gringraz.marvelcatalog.feature.common.data.MarvelCharactersResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MarvelApi {
    @GET("$apiVersion/public/characters")
    suspend fun getMarvelCharacters(
        @QueryMap queryStrings: Map<String, String> = emptyMap()
    ): Response<MarvelCharactersResponseModel>

    private companion object {
        private const val apiVersion = "v1"
    }
}