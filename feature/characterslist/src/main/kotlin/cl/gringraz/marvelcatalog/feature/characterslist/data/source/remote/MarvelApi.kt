package cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote

import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.model.MarvelCharactersResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface MarvelApi {
    @GET("$apiVersion/public/characters")
    suspend fun getMarvelCharacters(): Response<MarvelCharactersResponseModel>

    private companion object {
        private const val apiVersion = "v1"
    }
}