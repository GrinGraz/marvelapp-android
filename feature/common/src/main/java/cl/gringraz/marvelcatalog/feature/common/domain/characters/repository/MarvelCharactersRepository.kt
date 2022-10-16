package cl.gringraz.marvelcatalog.feature.common.domain.characters.repository

import arrow.core.Either
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharactersError

interface MarvelCharactersRepository {
    suspend fun getMarvelCharacters(): Either<MarvelCharactersError, List<MarvelCharacterModel>>
}
