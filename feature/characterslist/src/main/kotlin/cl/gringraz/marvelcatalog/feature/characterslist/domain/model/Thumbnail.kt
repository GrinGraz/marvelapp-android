package cl.gringraz.marvelcatalog.feature.characterslist.domain.model

data class Thumbnail(
    val path: String,
    val extension: String,
) {
    fun createImageUrl(): String {

    }
}