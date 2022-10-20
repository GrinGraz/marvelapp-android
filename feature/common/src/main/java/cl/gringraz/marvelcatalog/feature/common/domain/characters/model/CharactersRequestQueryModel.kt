package cl.gringraz.marvelcatalog.feature.common.domain.characters.model

data class CharactersRequestQueryModel(
    val nameStartsWith: String? = null,
    val limit: Int?,
    val offset: Int? = 0,
)
