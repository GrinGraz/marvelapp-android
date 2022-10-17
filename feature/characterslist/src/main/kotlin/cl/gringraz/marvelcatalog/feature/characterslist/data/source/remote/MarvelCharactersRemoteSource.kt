package cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote

import arrow.core.Either
import cl.gringraz.corenetwork.RemoteError
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.model.MarvelCharactersResponseModel

interface MarvelCharactersRemoteSource {
    suspend fun getMarvelCharacters(): Either<RemoteError, MarvelCharactersResponseModel?>
}
