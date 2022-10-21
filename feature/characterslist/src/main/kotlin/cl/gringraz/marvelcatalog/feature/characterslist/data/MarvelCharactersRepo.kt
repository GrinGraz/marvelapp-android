package cl.gringraz.marvelcatalog.feature.characterslist.data

import arrow.core.Either
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.MarvelCharactersRemoteSource
import cl.gringraz.marvelcatalog.feature.common.data.toDomainError
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.CharactersRequestQueryModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharactersError
import cl.gringraz.marvelcatalog.feature.characterslist.domain.usecase.repository.MarvelCharactersRepository

class MarvelCharactersRepo(
    private val remoteSource: MarvelCharactersRemoteSource,
) : MarvelCharactersRepository {

    override suspend fun getMarvelCharacters(
        requestModel: CharactersRequestQueryModel?,
    ): Either<MarvelCharactersError, List<MarvelCharacterModel>> {
        return remoteSource.getMarvelCharacters(requestModel).bimap(
            leftOperation = { it.toDomainError() },
            rightOperation = { it?.toMarvelCharactersModel() ?: emptyList() }
        )
    }
}
