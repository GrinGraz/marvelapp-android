package cl.gringraz.marvelcatalog.feature.characterdetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.gringraz.marvelcatalog.feature.characterdetail.domain.GetCharacterById
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarvelCharacterDetailViewModel(private val getCharacterByIdUseCase: GetCharacterById) :
    ViewModel() {

    private val _marvelCharactersUiState =
        MutableStateFlow<MarvelCharactersListUiState>(MarvelCharactersListUiState.Loading)
    val marvelCharactersUiState: StateFlow<MarvelCharactersListUiState> = _marvelCharactersUiState

    fun getCharacterById(id: String) {
        _marvelCharactersUiState.value = MarvelCharactersListUiState.Loading
        viewModelScope.launch {
            getCharacterByIdUseCase(id).fold(
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