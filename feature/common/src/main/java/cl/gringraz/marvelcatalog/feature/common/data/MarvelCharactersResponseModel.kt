package cl.gringraz.marvelcatalog.feature.common.data

import androidx.annotation.Keep
import cl.gringraz.corenetwork.RemoteError
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.Comic
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.Event
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharactersError
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.Serie
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.Story
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.ThumbnailModel
import com.google.gson.annotations.SerializedName

typealias Comics = Category
typealias Series = Category
typealias Stories = Category
typealias Events = Category

data class MarvelCharactersResponseModel(
    @SerializedName("code") val code: Int?,
    @SerializedName("status") val status: String?,
    @SerializedName("copyright") val copyright: String?,
    @SerializedName("attributionText") val attributionText: String?,
    @SerializedName("attributionHTML") val attributionHTML: String?,
    @SerializedName("etag") val etag: String?,
    @SerializedName("data") val data: Data?
) {
    fun toMarvelCharactersModel(): List<MarvelCharacterModel>? {
        return data?.results?.mapNotNull { character ->
            MarvelCharacterModel(
                id = character?.id?.toLong() ?: -1L,
                name = character?.name ?: "",
                description = character?.description,
                thumbnail = ThumbnailModel(
                    path = character?.thumbnail?.path ?: "",
                    extension = character?.thumbnail?.extension ?: ""
                ),
                comics = character?.comics?.items?.map {
                    Comic(
                        resourceURI = it.resourceURI ?: "",
                        name = it.name ?: ""
                    )
                }.orEmpty(),
                series = character?.series?.items?.map {
                    Serie(
                        resourceURI = it.resourceURI ?: "",
                        name = it.name ?: ""
                    )
                }.orEmpty(),
                stories = character?.stories?.items?.map {
                    Story(
                        resourceURI = it.resourceURI ?: "",
                        name = it.name ?: ""
                    )
                }.orEmpty(),
                events = character?.events?.items?.map {
                    Event(
                        resourceURI = it.resourceURI ?: "",
                        name = it.name ?: ""
                    )
                }.orEmpty()
            )
        }
    }
}

@Keep
data class Data(
    @SerializedName("offset") val offset: Int?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("total") val total: Int?,
    @SerializedName("count") val count: Int?,
    @SerializedName("results") val results: List<Results?>?
)

data class Results(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("modified") val modified: String?,
    @SerializedName("thumbnail") val thumbnail: Thumbnail?,
    @SerializedName("resourceURI") val resourceURI: String?,
    @SerializedName("comics") val comics: Comics?,
    @SerializedName("series") val series: Series?,
    @SerializedName("stories") val stories: Stories?,
    @SerializedName("events") val events: Events?,
    @SerializedName("urls") val urls: List<Urls>?
)

@Keep
data class Thumbnail(
    @SerializedName("path") val path: String?,
    @SerializedName("extension") val extension: String?
)

@Keep
data class Category(
    @SerializedName("available") val available: Int?,
    @SerializedName("collectionURI") val collectionURI: String?,
    @SerializedName("items") val items: List<Items>?,
    @SerializedName("returned") val returned: Int?
)

data class Urls(
    @SerializedName("type") val type: String?,
    @SerializedName("url") val url: String?
)

@Keep
data class Items(
    @SerializedName("resourceURI") val resourceURI: String?,
    @SerializedName("name") val name: String?
)

fun RemoteError.toDomainError(): MarvelCharactersError {
    return MarvelCharactersError(
        message = this.message
    )
}
