package cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote

import arrow.core.Either
import cl.gringraz.corenetwork.RemoteError
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.model.MarvelCharactersResponseModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.CharactersRequestQueryModel

interface MarvelCharactersRemoteSource {
    suspend fun getMarvelCharacters(
        requestModel: CharactersRequestQueryModel? = null
    ): Either<RemoteError, MarvelCharactersResponseModel?>
}
