package cl.gringraz.marvelcatalog.feature.characterdetail.data

import cl.gringraz.marvelcatalog.feature.characterdetail.domain.CharacterDetailsModel
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
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.Comic
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.Event
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.ItemModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharactersError
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.Serie
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.Story
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.ThumbnailModel
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object FakeDataFactory {

    private val fakeMarvelCharactersResponseModel = MarvelCharactersResponseModel(
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

    private val fakeMarvelCharactersModel = listOf(
        MarvelCharacterModel(
            id = 1,
            name = "name",
            description = "description",
            thumbnail = ThumbnailModel(
                path = "path",
                extension = "extension"
            ),
            comics = listOf(Comic("resourceUri", "name")),
            series = listOf(Serie("resourceUri", "name")),
            stories = listOf(Story("resourceUri", "name")),
            events = listOf(Event("resourceUri", "name"))
        ),
        MarvelCharacterModel(
            id = 2,
            name = "second name",
            description = "description",
            thumbnail = ThumbnailModel(
                path = "path",
                extension = "extension"
            ),
            comics = listOf(Comic("resourceUri", "name")),
            series = listOf(Serie("resourceUri", "name")),
            stories = listOf(Story("resourceUri", "name")),
            events = listOf(Event("resourceUri", "name"))
        )
    )

    val fakeCharacterDetailsModel = listOf(
        CharacterDetailsModel("Comics", listOf(ItemModel("resourceUri", "name"))),
        CharacterDetailsModel("Stories", listOf(ItemModel("resourceUri", "name"))),
        CharacterDetailsModel("Series", listOf(ItemModel("resourceUri", "name"))),
        CharacterDetailsModel("Events", listOf(ItemModel("resourceUri", "name")))
    )

    val fakeConnectionMarvelError = MarvelCharactersError(
        message = "Connection error"
    )

    val fakeUnknownMarvelError = MarvelCharactersError(
        message = "Unknown error"
    )

    val errorResponse: Response<MarvelCharactersResponseModel> =
        Response.error(404, "not found".toResponseBody(null))

    fun fakeQueriedMarvelCharactersModel(id: String) = fakeMarvelCharactersModel.filter {
        it.id.toString() == id
    }

    fun fakeQueriedResponseModel(id: String): MarvelCharactersResponseModel {
        val data = fakeMarvelCharactersResponseModel.data?.copy(
            results = fakeMarvelCharactersResponseModel.data?.results?.filter {
                it?.id.toString() == id
            }
        )
        return fakeMarvelCharactersResponseModel.copy(data = data)
    }

    fun fakeQueriedMarvelCharactersResponse(id: String): Response<MarvelCharactersResponseModel> {
        return Response.success(fakeQueriedResponseModel(id))
    }
}
