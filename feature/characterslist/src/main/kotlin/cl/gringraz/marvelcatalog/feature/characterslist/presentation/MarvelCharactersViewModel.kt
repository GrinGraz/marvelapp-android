package cl.gringraz.marvelcatalog.feature.characterslist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.gringraz.marvelcatalog.feature.characterslist.domain.usecase.GetMarvelCharacters
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarvelCharactersViewModel(
    private val getMarvelCharactersUseCase: GetMarvelCharacters,
) : ViewModel() {

    private val _marvelCharactersUiState =
        MutableStateFlow<MarvelCharactersListUiState>(MarvelCharactersListUiState.Loading)
    val marvelCharactersUiState: StateFlow<MarvelCharactersListUiState> = _marvelCharactersUiState

    fun getMarvelCharacters() {
        viewModelScope.launch {
            _marvelCharactersUiState.value = MarvelCharactersListUiState.Loading
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
