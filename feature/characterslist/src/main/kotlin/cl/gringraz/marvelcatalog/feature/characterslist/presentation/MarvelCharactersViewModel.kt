package cl.gringraz.marvelcatalog.feature.characterslist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.gringraz.marvelcatalog.feature.characterslist.domain.usecase.GetMarvelCharacters
import cl.gringraz.marvelcatalog.feature.characterslist.ui.model.ListingType
import cl.gringraz.marvelcatalog.feature.characterslist.ui.model.ListingType.Explore
import cl.gringraz.marvelcatalog.feature.characterslist.ui.model.ListingType.Search
import cl.gringraz.marvelcatalog.feature.characterslist.ui.model.MarvelCharactersListUiState
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.CharactersRequestQueryModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

class MarvelCharactersViewModel(
    private val getMarvelCharactersUseCase: GetMarvelCharacters,
) : ViewModel() {

    private val _marvelCharactersUiState =
        MutableStateFlow<MarvelCharactersListUiState>(MarvelCharactersListUiState.Initial)
    val marvelCharactersUiState: StateFlow<MarvelCharactersListUiState> = _marvelCharactersUiState
    private lateinit var getCharactersJob: Job
    private var atomicOffset = AtomicInteger(0)
    private var previousQuery: String? = ""
    var listType: ListingType = Explore
        private set

    fun getMarvelCharacters(requestModel: CharactersRequestQueryModel? = null) {
        getCharactersJob = viewModelScope.launch {
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

    fun setListingType(listingType: String) {
        listType = when (listingType) {
            "search" -> Search
            "explore" -> Explore
            else -> Explore
        }
    }

    fun getAndAccumulateOffset(): Int {
        return atomicOffset.getAndAccumulate(20) { initial, new -> initial + new }
    }

    fun compareAndSetPreviousQuery(query: String?): Boolean {
        if (query != previousQuery) {
            previousQuery = query
            return true
        }
        return false
    }
}
