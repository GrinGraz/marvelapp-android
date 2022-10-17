package cl.gringraz.marvelcatalog.feature.characterslist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.gringraz.marvelcatalog.feature.characterslist.domain.usecase.GetMarvelCharacters
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val getMarvelCharactersUseCase: GetMarvelCharacters,
) : ViewModel() {

    val result = MutableStateFlow<MarvelCharactersListUiState>(MarvelCharactersListUiState.Loading)

    fun getMarvelCharacters() {
        viewModelScope.launch(Dispatchers.Main) {
            getMarvelCharactersUseCase().fold(
                ifLeft = { result.value = MarvelCharactersListUiState.Error(it.message) },
                ifRight = { result.value = MarvelCharactersListUiState.Success(it) }
            )
        }
    }
}

sealed class MarvelCharactersListUiState {
    object Loading : MarvelCharactersListUiState()
    data class Error(val message: String) : MarvelCharactersListUiState()
    data class Success(val characters: List<MarvelCharacterModel>) : MarvelCharactersListUiState()
}
