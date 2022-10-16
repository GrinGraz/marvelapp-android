package cl.gringraz.marvelcatalog.feature.common.domain.characters.model

data class MarvelCharactersError(
    override val message: String
): DomainError
