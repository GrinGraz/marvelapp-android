package cl.gringraz.marvelcatalog.feature.characterdetail.data

import arrow.core.Either
import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote.MarvelCharacterRemoteSource
import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote.model.toDomainError
import cl.gringraz.marvelcatalog.feature.characterdetail.domain.MarvelCharacterRepository
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharactersError

class MarvelCharacterRepo(private val remoteSource: MarvelCharacterRemoteSource) :
    MarvelCharacterRepository {
    override suspend fun getMarvelCharacterById(id: String):
            Either<MarvelCharactersError, List<MarvelCharacterModel>> {
        return remoteSource.getMarvelCharacterById(id).bimap(
            leftOperation = { it.toDomainError() },
            rightOperation = { it?.toMarvelCharactersModel() ?: emptyList() }
        )
    }
}
