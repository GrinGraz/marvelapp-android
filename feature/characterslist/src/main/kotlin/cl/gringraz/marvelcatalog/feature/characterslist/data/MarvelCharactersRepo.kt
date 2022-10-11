package cl.gringraz.marvelcatalog.feature.characterslist.data

import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.repository.MarvelCharactersRepository

class MarvelCharactersRepo: MarvelCharactersRepository  {

    override suspend fun getMarvelCharacters(): List<MarvelCharacterModel> {
        TODO("Not yet implemented")
    }
}