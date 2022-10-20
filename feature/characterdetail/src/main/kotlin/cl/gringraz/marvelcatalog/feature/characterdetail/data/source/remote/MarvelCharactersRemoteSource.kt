package cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote

import arrow.core.Either
import cl.gringraz.corenetwork.RemoteError
import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote.model.MarvelCharactersResponseModel

interface MarvelCharactersRemoteSource {
    suspend fun getMarvelCharacterById(
        id: String
    ): Either<RemoteError, MarvelCharactersResponseModel?>
}
