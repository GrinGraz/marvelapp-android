package cl.gringraz.marvelcatalog.feature.common.domain.characters.repository

import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel

interface MarvelCharactersRepository {
    suspend fun getMarvelCharacters(): List<MarvelCharacterModel>
}
