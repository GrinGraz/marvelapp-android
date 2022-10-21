package cl.gringraz.marvelcatalog.feature.characterslist.domain.usecase.repository

import arrow.core.Either
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.CharactersRequestQueryModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharactersError

interface MarvelCharactersRepository {
    suspend fun getMarvelCharacters(
        requestModel: CharactersRequestQueryModel? = null
    ): Either<MarvelCharactersError, List<MarvelCharacterModel>>
}
