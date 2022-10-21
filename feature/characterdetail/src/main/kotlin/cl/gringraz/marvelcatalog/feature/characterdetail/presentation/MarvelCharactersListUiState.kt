package cl.gringraz.marvelcatalog.feature.characterdetail.presentation

import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel

sealed interface MarvelCharactersListUiState {
    object Loading : MarvelCharactersListUiState
    data class Error(val message: String) : MarvelCharactersListUiState
    data class Success(val character: List<MarvelCharacterModel>) : MarvelCharactersListUiState
}
