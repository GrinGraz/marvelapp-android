package cl.gringraz.marvelcatalog.feature.characterslist

import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.model.Comics
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.model.Data
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.model.Events
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.model.Items
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.model.MarvelCharactersResponseModel
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.model.Results
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.model.Series
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.model.Stories
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.model.Thumbnail
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.model.Urls
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharactersError
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.ThumbnailModel
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object FakeDataFactory {

    val fakeMarvelCharactersResponseModel = MarvelCharactersResponseModel(
        code = 1,
        status = "status",
        copyright = "copyright",
        attributionText = "attributionText",
        attributionHTML = "attributionHTML",
        etag = "etag",
        data = Data(
            offset = 1,
            limit = 1,
            total = 1,
            count = 1,
            results = listOf(
                Results(
                    id = 1,
                    name = "name",
                    description = "description",
                    modified = "modified",
                    Thumbnail(
                        path = "path",
                        extension = "extension"
                    ),
                    resourceURI = "resourceUri",
                    comics = Comics(
                        available = 1,
                        collectionURI = "collectionUri",
                        items = listOf(
                            Items(
                                resourceURI = "resourceUri",
                                name = "name"
                            )
                        ),
                        returned = 1
                    ),
                    series = Series(
                        available = 1,
                        collectionURI = "collectionUri",
                        items = listOf(
                            Items(
                                resourceURI = "resourceUri",
                                name = "name"
                            )
                        ),
                        returned = 1
                    ),
                    stories = Stories(
                        available = 1,
                        collectionURI = "collectionUri",
                        items = listOf(
                            Items(
                                resourceURI = "resourceUri",
                                name = "name"
                            )
                        ),
                        returned = 1
                    ),
                    events = Events(
                        available = 1,
                        collectionURI = "collectionUri",
                        items = listOf(
                            Items(
                                resourceURI = "resourceUri",
                                name = "name"
                            )
                        ),
                        returned = 1
                    ),
                    urls = listOf(
                        Urls(
                            type = "type",
                            url = "url"
                        )
                    )
                )
            )
        )
    )

    val fakeMarvelCharactersModel = listOf(
        MarvelCharacterModel(
            id = 1,
            name = "name",
            description = "description",
            thumbnail = ThumbnailModel(
                path = "path",
                extension = "extension"
            )
        )
    )

    val fakeConnectionMarvelError = MarvelCharactersError(
        message = "Connection error"
    )

    val fakeUnknownMarvelError = MarvelCharactersError(
        message = "Unknown error"
    )

    val fakeMarvelCharactersResponse: Response<MarvelCharactersResponseModel> =
        Response.success(fakeMarvelCharactersResponseModel)

    val errorResponse: Response<MarvelCharactersResponseModel> =
        Response.error(404, "not found".toResponseBody(null))
}
