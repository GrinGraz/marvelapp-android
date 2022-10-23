package cl.gringraz.marvelcatalog.feature.characterdetail.presentation

import cl.gringraz.marvelcatalog.feature.characterdetail.domain.CharacterDetailsModel

sealed interface MarvelCharacterDetailUiState {
    object Loading : MarvelCharacterDetailUiState
    data class Error(val message: String) : MarvelCharacterDetailUiState
    data class Success(val characterDetails: List<CharacterDetailsModel>) :
        MarvelCharacterDetailUiState
}
