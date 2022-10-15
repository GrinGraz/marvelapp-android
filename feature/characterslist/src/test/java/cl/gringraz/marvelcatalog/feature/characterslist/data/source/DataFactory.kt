package cl.gringraz.marvelcatalog.feature.characterslist.data.source

import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.model.MarvelCharactersResponseModel
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object DataFactory {

    val fakeMarvelCharactersResponseModel = MarvelCharactersResponseModel(
        code = null,
        status = null,
        copyright = null,
        attributionText = null,
        attributionHTML = null,
        etag = null,
        data = null
    )

    val fakeMarvelCharactersResponse: Response<MarvelCharactersResponseModel> =
        Response.success(fakeMarvelCharactersResponseModel)

    val errorResponse: Response<MarvelCharactersResponseModel> =
        Response.error(404, "not found".toResponseBody(null))
}