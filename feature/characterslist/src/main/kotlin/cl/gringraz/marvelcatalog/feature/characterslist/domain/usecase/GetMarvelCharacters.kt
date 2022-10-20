package cl.gringraz.marvelcatalog.feature.characterslist.domain.usecase

import arrow.core.Either
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.CharactersRequestQueryModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharactersError

fun interface GetMarvelCharacters {
    suspend operator fun invoke(
        requestModel: CharactersRequestQueryModel?,
    ): Either<MarvelCharactersError, List<MarvelCharacterModel>>
}