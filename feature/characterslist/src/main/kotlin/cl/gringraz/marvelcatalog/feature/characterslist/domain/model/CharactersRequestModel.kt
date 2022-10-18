package cl.gringraz.marvelcatalog.feature.characterslist.domain.model

data class CharactersRequestModel(
    val name: String,
    val nameStartWith: String,
    val limit: Int,
    val offset: Int,
)
