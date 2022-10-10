package cl.gringraz.marvelcatalog.feature.characterslist.domain.usecase

import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel

fun interface GetMarvelCharacters {
    suspend operator fun invoke(): List<MarvelCharacterModel>
}