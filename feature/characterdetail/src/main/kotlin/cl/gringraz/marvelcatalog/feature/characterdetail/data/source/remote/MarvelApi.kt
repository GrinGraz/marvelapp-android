package cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote

import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote.model.MarvelCharactersResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApi {
    @GET("$apiVersion/public/characters/{id}")
    suspend fun getMarvelCharacterById(
        @Path("id") id: String
    ): Response<MarvelCharactersResponseModel>

    private companion object {
        private const val apiVersion = "v1"
    }
}