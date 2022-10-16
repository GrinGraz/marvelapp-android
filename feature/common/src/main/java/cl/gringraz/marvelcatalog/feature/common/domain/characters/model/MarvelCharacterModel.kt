package cl.gringraz.marvelcatalog.feature.common.domain.characters.model

data class MarvelCharacterModel(
    val id: Long,
    val name: String,
    val description: String?,
    val thumbnail: ThumbnailModel
)
