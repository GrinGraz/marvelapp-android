package cl.gringraz.marvelcatalog.feature.characterdetail.domain

import arrow.core.Either
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharactersError

interface MarvelCharacterRepository {
    suspend fun getMarvelCharacterById(
        id: String
    ): Either<MarvelCharactersError, List<MarvelCharacterModel>>
}