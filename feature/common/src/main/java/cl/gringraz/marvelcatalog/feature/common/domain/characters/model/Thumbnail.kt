package cl.gringraz.marvelcatalog.feature.common.domain.characters.model

data class Thumbnail(
    val path: String,
    val extension: String
) {
    fun createThumbnailUrl(): String = "$path.$extension"
}
