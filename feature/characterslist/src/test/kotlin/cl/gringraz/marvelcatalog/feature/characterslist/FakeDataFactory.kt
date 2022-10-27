package cl.gringraz.marvelcatalog.feature.characterslist

import cl.gringraz.marvelcatalog.feature.common.data.Comics
import cl.gringraz.marvelcatalog.feature.common.data.Data
import cl.gringraz.marvelcatalog.feature.common.data.Events
import cl.gringraz.marvelcatalog.feature.common.data.Items
import cl.gringraz.marvelcatalog.feature.common.data.MarvelCharactersResponseModel
import cl.gringraz.marvelcatalog.feature.common.data.Results
import cl.gringraz.marvelcatalog.feature.common.data.Series
import cl.gringraz.marvelcatalog.feature.common.data.Stories
import cl.gringraz.marvelcatalog.feature.common.data.Thumbnail
import cl.gringraz.marvelcatalog.feature.common.data.Urls
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.CharactersRequestQueryModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.ItemModel
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
                ),
                Results(
                    id = 2,
                    name = "second name",
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
            ),
            comics = listOf(ItemModel("resourceUri", "name")),
            series = listOf(ItemModel("resourceUri", "name")),
            stories = listOf(ItemModel("resourceUri", "name")),
            events = listOf(ItemModel("resourceUri", "name"))
        ),
        MarvelCharacterModel(
            id = 2,
            name = "second name",
            description = "description",
            thumbnail = ThumbnailModel(
                path = "path",
                extension = "extension"
            ),
            comics = listOf(ItemModel("resourceUri", "name")),
            series = listOf(ItemModel("resourceUri", "name")),
            stories = listOf(ItemModel("resourceUri", "name")),
            events = listOf(ItemModel("resourceUri", "name"))
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

    val fakeCharactersRequestQueryModel: CharactersRequestQueryModel = CharactersRequestQueryModel(
        nameStartsWith = "second",
        limit = 100
    )

    val errorResponse: Response<MarvelCharactersResponseModel> =
        Response.error(404, "not found".toResponseBody(null))

    fun fakeQueriedMarvelCharactersModel(query: String) = fakeMarvelCharactersModel.filter {
        it.name == query
    }

    fun fakeQueriedResponseModel(query: String): MarvelCharactersResponseModel {
        val data = fakeMarvelCharactersResponseModel.data?.copy(
            results = fakeMarvelCharactersResponseModel.data?.results?.filter {
                it?.name?.contains(
                    query
                )!!
            }
        )
        return fakeMarvelCharactersResponseModel.copy(data = data)
    }

    fun fakeQueriedMarvelCharactersResponse(query: String): Response<MarvelCharactersResponseModel> {
        return Response.success(fakeQueriedResponseModel(query))
    }

    fun fakeQueryMapFrom(requestQueryModel: CharactersRequestQueryModel): Map<String, String> {
        return buildMap {
            with(requestQueryModel) {
                nameStartsWith?.let { put("nameStartsWith", it) }
                limit?.let { put("limit", it.toString()) }
                offset?.let { put("offset", it.toString()) }
            }
        }
    }
}
