package cl.gringraz.marvelcatalog.feature.characterslist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import cl.gringraz.marvelcatalog.feature.characterslist.R
import cl.gringraz.marvelcatalog.feature.characterslist.databinding.FragmentCharacterListBinding
import cl.gringraz.marvelcatalog.feature.characterslist.di.factory
import cl.gringraz.marvelcatalog.feature.characterslist.presentation.MarvelCharactersViewModel
import cl.gringraz.marvelcatalog.feature.characterslist.ui.Navigation.navigateToCharacterDetail
import cl.gringraz.marvelcatalog.feature.characterslist.ui.adapter.MarvelCharactersListAdapter
import cl.gringraz.marvelcatalog.feature.characterslist.ui.model.ListingType.Explore
import cl.gringraz.marvelcatalog.feature.characterslist.ui.model.ListingType.Search
import cl.gringraz.marvelcatalog.feature.characterslist.ui.model.MarvelCharactersListUiState
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.CharactersRequestQueryModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel

class MarvelCharactersListFragment : Fragment() {

    private val viewModel: MarvelCharactersViewModel by viewModels { factory }
    private val charactersAdapter = MarvelCharactersListAdapter(::onItemClick, ::onLoadMoreItems)
    private lateinit var layoutManager: LinearLayoutManager
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listingType = requireArguments().getString("listing_type", "explore")
        viewModel.setListingType(listingType)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUiStateCollection()
        when (viewModel.listType) {
            Explore -> setupExplore()
            Search -> setupSearch()
        }
        setupRecyclerView()
    }

    private fun setupSearch() {
        setupFragmentMenu()
        renderEmptySearch()
    }

    private fun setupExplore() {
        loadItems()
        renderLoading()
    }

    private fun setupFragmentMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.character_list_menu, menu)
                setSearchView(menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setSearchView(menu: Menu) {
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.debounceQueryTextListener { newText ->
            val isDifferentQuery = viewModel.compareAndSetPreviousQuery(query = newText)
            if (isDifferentQuery and !newText.isNullOrBlank()) {
                val queryModel = CharactersRequestQueryModel(
                    nameStartsWith = newText
                )
                viewModel.getMarvelCharacters(queryModel)
            }
        }
    }

    private fun setupRecyclerView() {
        layoutManager = LinearLayoutManager(requireContext())
        with(binding.charactersRecycler) {
            layoutManager = this@MarvelCharactersListFragment.layoutManager
            adapter = charactersAdapter
            setHasFixedSize(true)
        }
        if (viewModel.listType == Explore) charactersAdapter.addPagination()
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
            MarvelCharactersListUiState.Initial -> binding.progressBar.visibility = View.INVISIBLE
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
        if (viewModel.listType == Explore) charactersAdapter.submitList(characters.toMutableList())
        else charactersAdapter.submitSearch(characters)
    }

    private fun renderEmptySearch() {
        with(binding) {
            progressBar.visibility = View.INVISIBLE
            statusMessage.visibility = View.VISIBLE
            statusMessage.text = getString(R.string.search_text)
        }
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
