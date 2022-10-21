package cl.gringraz.marvelcatalog.feature.characterslist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.gringraz.marvelcatalog.feature.characterslist.domain.usecase.GetMarvelCharacters
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.CharactersRequestQueryModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

class MarvelCharactersViewModel(
    private val getMarvelCharactersUseCase: GetMarvelCharacters,
) : ViewModel() {

    private val _marvelCharactersUiState =
        MutableStateFlow<MarvelCharactersListUiState>(MarvelCharactersListUiState.Loading)
    val marvelCharactersUiState: StateFlow<MarvelCharactersListUiState> = _marvelCharactersUiState
    private var atomicOffset = AtomicInteger(0)

    fun getMarvelCharacters(requestModel: CharactersRequestQueryModel? = null) {
        viewModelScope.launch {
            _marvelCharactersUiState.value = MarvelCharactersListUiState.Loading
            getMarvelCharactersUseCase(requestModel).fold(
                ifLeft = {
                    _marvelCharactersUiState.value = MarvelCharactersListUiState.Error(it.message)
                },
                ifRight = {
                    _marvelCharactersUiState.value = MarvelCharactersListUiState.Success(it)
                }
            )
        }
    }

    fun getAndAccumulateOffset(): Int {
        return atomicOffset.getAndAccumulate(20) { initial, new -> initial + new }
    }
}
