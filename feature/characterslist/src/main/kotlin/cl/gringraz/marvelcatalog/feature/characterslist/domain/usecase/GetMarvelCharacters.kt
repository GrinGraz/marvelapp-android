package cl.gringraz.marvelcatalog.feature.characterslist.domain.usecase

import arrow.core.Either
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharactersError

fun interface GetMarvelCharacters {
    suspend operator fun invoke(): Either<MarvelCharactersError, List<MarvelCharacterModel>>
}