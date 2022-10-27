package cl.gringraz.marvelcatalog.feature.characterdetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.gringraz.marvelcatalog.feature.characterdetail.domain.CharacterDetailsModel
import cl.gringraz.marvelcatalog.feature.characterdetail.domain.GetCharacterById
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.ItemModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarvelCharacterDetailViewModel(private val getCharacterByIdUseCase: GetCharacterById) :
    ViewModel() {

    private val _marvelCharacterUiState =
        MutableStateFlow<MarvelCharacterDetailUiState>(MarvelCharacterDetailUiState.Loading)
    val marvelCharacterUiState: StateFlow<MarvelCharacterDetailUiState> = _marvelCharacterUiState

    fun getCharacterById(id: String) {
        _marvelCharacterUiState.value = MarvelCharacterDetailUiState.Loading
        viewModelScope.launch {
            getCharacterByIdUseCase(id).fold(
                ifLeft = {
                    _marvelCharacterUiState.value = MarvelCharacterDetailUiState.Error(it.message)
                },
                ifRight = {
                    val detailsList = createDetailsListOf(character = it.first())
                    _marvelCharacterUiState.value =
                        MarvelCharacterDetailUiState.Success(detailsList)
                }
            )
        }
    }

    private fun createDetailsListOf(character: MarvelCharacterModel): List<CharacterDetailsModel> =
        buildList {
            val stubList = listOf(ItemModel("", "Nothing to show!"))
            with(character) {
                add(CharacterDetailsModel("Comics", comics.ifEmpty { stubList }))
                add(CharacterDetailsModel("Stories", stories.ifEmpty { stubList }))
                add(CharacterDetailsModel("Series", series.ifEmpty { stubList }))
                add(CharacterDetailsModel("Events", events.ifEmpty { stubList }))
            }
        }
}
