package cl.gringraz.marvelcatalog.feature.common.domain.characters.model

data class MarvelCharacterModel(
    val id: Long,
    val name: String,
    private val description: String?,
    val thumbnail: ThumbnailModel
) {
    fun getDescriptionOrDefault(): String {
        return if (description.isNullOrEmpty()) {
            "We don't have the character's description right now."
        } else {
            description
        }
    }
}
