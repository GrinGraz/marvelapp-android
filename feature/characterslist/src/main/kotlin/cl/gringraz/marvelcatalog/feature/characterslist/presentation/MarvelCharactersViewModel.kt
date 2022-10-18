package cl.gringraz.marvelcatalog.feature.characterslist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.gringraz.marvelcatalog.feature.characterslist.domain.usecase.GetMarvelCharacters
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val getMarvelCharactersUseCase: GetMarvelCharacters,
) : ViewModel() {

    private val _marvelCharactersUiState =
        MutableStateFlow<MarvelCharactersListUiState>(MarvelCharactersListUiState.Loading)
    val marvelCharactersUiState: StateFlow<MarvelCharactersListUiState> = _marvelCharactersUiState

    fun getMarvelCharacters() {
        viewModelScope.launch {
            getMarvelCharactersUseCase().fold(
                ifLeft = {
                    _marvelCharactersUiState.value = MarvelCharactersListUiState.Error(it.message)
                },
                ifRight = {
                    _marvelCharactersUiState.value = MarvelCharactersListUiState.Success(it)
                }
            )
        }
    }
}

sealed interface MarvelCharactersListUiState {
    object Loading : MarvelCharactersListUiState
    data class Error(val message: String) : MarvelCharactersListUiState
    data class Success(val characters: List<MarvelCharacterModel>) : MarvelCharactersListUiState
}
