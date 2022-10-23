package cl.gringraz.marvelcatalog.feature.characterdetail.domain

import arrow.core.Either
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharactersError

fun interface GetCharacterById {
    suspend operator fun invoke(
        id: String,
    ): Either<MarvelCharactersError, List<MarvelCharacterModel>>
}
