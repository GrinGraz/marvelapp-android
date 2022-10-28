package cl.gringraz.marvelcatalog.feature.common.domain.characters.model

data class MarvelCharacterModel(
    val id: Long,
    val name: String,
    private val description: String?,
    val thumbnail: ThumbnailModel,
    val comics: List<Comic>,
    val series: List<Serie>,
    val stories: List<Story>,
    val events: List<Event>
) {
    fun getDescriptionOrDefault(): String {
        return if (description.isNullOrBlank()) {
            "We don't have the character description right now."
        } else {
            description
        }
    }
}
