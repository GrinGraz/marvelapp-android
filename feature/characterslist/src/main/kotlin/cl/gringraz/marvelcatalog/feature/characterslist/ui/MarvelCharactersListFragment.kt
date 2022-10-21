package cl.gringraz.marvelcatalog.feature.characterslist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cl.gringraz.marvelcatalog.feature.characterslist.databinding.FragmentCharacterListBinding
import cl.gringraz.marvelcatalog.feature.characterslist.di.charactersViewModel
import cl.gringraz.marvelcatalog.feature.characterslist.presentation.MarvelCharactersListUiState
import cl.gringraz.marvelcatalog.feature.characterslist.presentation.MarvelCharactersViewModel
import cl.gringraz.marvelcatalog.feature.characterslist.presentation.Navigation.navigateToCharacterDetail
import cl.gringraz.marvelcatalog.feature.characterslist.ui.adapter.MarvelCharactersListAdapter
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.CharactersRequestQueryModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel

class MarvelCharactersListFragment : Fragment() {

    private val viewModel: MarvelCharactersViewModel = charactersViewModel()
    private val charactersAdapter = MarvelCharactersListAdapter(::onItemClick, ::onLoadMoreItems)
    private lateinit var layoutManager: LinearLayoutManager
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupUiStateCollection()
        loadItems()
    }

    private fun setupRecyclerView() {
        layoutManager = LinearLayoutManager(requireContext())
        with(binding.charactersRecycler) {
            layoutManager = this@MarvelCharactersListFragment.layoutManager
            adapter = charactersAdapter
            setHasFixedSize(true)
        }
        charactersAdapter.addPagination()
    }

    private fun setupUiStateCollection() = safeLifecycle {
        viewModel.marvelCharactersUiState.collect(::renderCharactersUiState)
    }

    private fun loadItems() {
        val requestQueryModel = CharactersRequestQueryModel(
            offset = viewModel.getAndAccumulateOffset()
        )
        viewModel.getMarvelCharacters(requestQueryModel)
    }

    private fun renderCharactersUiState(state: MarvelCharactersListUiState) {
        when (state) {
            MarvelCharactersListUiState.Loading -> renderLoading()
            is MarvelCharactersListUiState.Error -> renderError(state.message)
            is MarvelCharactersListUiState.Success -> renderCharacters(state.characters)
        }
    }

    private fun renderLoading() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            statusMessage.visibility = View.INVISIBLE
        }
    }

    private fun renderError(message: String) {
        with(binding) {
            charactersRecycler.visibility = View.INVISIBLE
            progressBar.visibility = View.INVISIBLE
            statusMessage.visibility = View.VISIBLE
            statusMessage.text = message
        }
    }

    private fun renderCharacters(characters: List<MarvelCharacterModel>) {
        with(binding) {
            progressBar.visibility = View.INVISIBLE
            statusMessage.visibility = View.INVISIBLE
        }
        charactersAdapter.submitList(characters.toMutableList())
    }

    private fun onItemClick(item: MarvelCharacterModel) {
        navigateToCharacterDetail(item)
    }

    private fun onLoadMoreItems() {
        loadItems()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}