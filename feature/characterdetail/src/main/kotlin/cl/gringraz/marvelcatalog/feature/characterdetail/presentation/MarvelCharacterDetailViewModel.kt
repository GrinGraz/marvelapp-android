package cl.gringraz.marvelcatalog.feature.characterdetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.gringraz.marvelcatalog.feature.characterdetail.domain.GetCharacterById
import cl.gringraz.marvelcatalog.feature.characterdetail.domain.CharacterDetailsModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.Comic
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarvelCharacterDetailViewModel(private val getCharacterByIdUseCase: GetCharacterById) :
    ViewModel() {

    private val _marvelCharactersUiState =
        MutableStateFlow<MarvelCharacterDetailUiState>(MarvelCharacterDetailUiState.Loading)
    val marvelCharactersUiState: StateFlow<MarvelCharacterDetailUiState> = _marvelCharactersUiState

    fun getCharacterById(id: String) {
        _marvelCharactersUiState.value = MarvelCharacterDetailUiState.Loading
        viewModelScope.launch {
            getCharacterByIdUseCase(id).fold(
                ifLeft = {
                    _marvelCharactersUiState.value = MarvelCharacterDetailUiState.Error(it.message)
                },
                ifRight = {
                    val detailsList = createDetailsListOf(character = it.first())
                    _marvelCharactersUiState.value =
                        MarvelCharacterDetailUiState.Success(detailsList)
                }
            )
        }
    }

    private fun createDetailsListOf(character: MarvelCharacterModel): List<CharacterDetailsModel> =
        buildList {
            val stubList = listOf(Comic("", "No to show!"))
            with(character) {
                add(CharacterDetailsModel("Comics", comics.ifEmpty { stubList }))
                add(CharacterDetailsModel("Stories", stories.ifEmpty { stubList }))
                add(CharacterDetailsModel("Series", series.ifEmpty { stubList }))
                add(CharacterDetailsModel("Events", events.ifEmpty { stubList }))
            }
        }
}
