package cl.gringraz.marvelcatalog.feature.characterslist.data

import arrow.core.Either
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.MarvelCharactersRemoteSource
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.model.toDomainError
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharactersError
import cl.gringraz.marvelcatalog.feature.common.domain.characters.repository.MarvelCharactersRepository

class MarvelCharactersRepo(
    private val remoteSource: MarvelCharactersRemoteSource,
) : MarvelCharactersRepository {

    override suspend fun getMarvelCharacters(): Either<MarvelCharactersError, List<MarvelCharacterModel>> {
        return remoteSource.getMarvelCharacters().bimap(
            leftOperation = { it.toDomainError() },
            rightOperation = { it?.toMarvelCharactersModel() ?: emptyList() }
        )
    }
}
