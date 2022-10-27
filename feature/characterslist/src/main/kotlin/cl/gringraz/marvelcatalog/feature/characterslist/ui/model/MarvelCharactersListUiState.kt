package cl.gringraz.marvelcatalog.feature.characterslist.ui.model

import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel

sealed interface MarvelCharactersListUiState {
    object Initial: MarvelCharactersListUiState
    object Loading : MarvelCharactersListUiState
    data class Error(val message: String) : MarvelCharactersListUiState
    data class Success(val characters: List<MarvelCharacterModel>) : MarvelCharactersListUiState
}
