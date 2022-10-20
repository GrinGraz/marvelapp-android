package cl.gringraz.marvelcatalog.feature.characterslist.presentation.ui

import android.net.Uri
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
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.gringraz.marvelcatalog.feature.characterslist.R
import cl.gringraz.marvelcatalog.feature.characterslist.databinding.FragmentCharacterListBinding
import cl.gringraz.marvelcatalog.feature.characterslist.di.charactersViewModel
import cl.gringraz.marvelcatalog.feature.characterslist.presentation.MarvelCharactersListUiState
import cl.gringraz.marvelcatalog.feature.characterslist.presentation.MarvelCharactersViewModel
import cl.gringraz.marvelcatalog.feature.characterslist.presentation.debounceQueryTextListener
import cl.gringraz.marvelcatalog.feature.characterslist.presentation.safeLifecycle
import cl.gringraz.marvelcatalog.feature.characterslist.presentation.ui.adapter.MarvelCharactersListAdapter
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.CharactersRequestQueryModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel

class MarvelCharactersListFragment : Fragment() {

    private val viewModel: MarvelCharactersViewModel = charactersViewModel()
    private val charactersAdapter = MarvelCharactersListAdapter(::onItemClick, ::onNoResultSearch)
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
        setupFragmentMenu()
        setupRecyclerView()
        setupUiStateCollection()
        viewModel.getMarvelCharacters()
    }

    private fun setupFragmentMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.character_list_menu, menu)
                setSearchView(menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.refresh -> viewModel.getMarvelCharacters()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setSearchView(menu: Menu) {
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setOnCloseListener {
            charactersAdapter.filter.filter("")
            return@setOnCloseListener false
        }

        searchView.debounceQueryTextListener { newText ->
            val isDifferentQuery = viewModel.compareAndSetPreviousQuery(query = newText)
            if (isDifferentQuery) {
                charactersAdapter.filter.filter(newText)
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
    }

    private fun setupUiStateCollection() = safeLifecycle {
        viewModel.marvelCharactersUiState.collect(::renderCharactersUiState)
    }

    private fun renderCharactersUiState(state: MarvelCharactersListUiState) {
        when (state) {
            MarvelCharactersListUiState.Loading    -> renderLoading()
            is MarvelCharactersListUiState.Error   -> renderError(state.message)
            is MarvelCharactersListUiState.Success -> renderCharacters(state.characters)
        }
    }

    private fun renderLoading() {
        with(binding) {
            charactersRecycler.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
            binding.errorMessage.visibility = View.INVISIBLE
        }
    }

    private fun renderError(message: String) {
        with(binding) {
            charactersRecycler.visibility = View.INVISIBLE
            progressBar.visibility = View.INVISIBLE
            errorMessage.visibility = View.VISIBLE
            errorMessage.text = message
        }
    }

    private fun renderCharacters(characters: List<MarvelCharacterModel>) {
        with(binding) {
            charactersRecycler.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
            errorMessage.visibility = View.INVISIBLE
        }
        charactersAdapter.submitList(characters)
    }

    private fun onItemClick(item: MarvelCharacterModel) {
        val uri = Uri.Builder()
            .scheme("android")
            .authority("marvel.app")
            .appendPath("character_detail")
            .appendPath(item.id.toString())
            .appendPath(item.name)
            .appendPath(item.getDescriptionOrDefault())
            .appendPath(item.thumbnail.createThumbnailUrl())
            .build()

        val request = NavDeepLinkRequest.Builder
            .fromUri(uri)
            .build()
        findNavController().navigate(request)
    }

    private fun onNoResultSearch(query: String) {
        val request = CharactersRequestQueryModel(
            nameStartsWith = query,
            limit = 100
        )
        viewModel.getMarvelCharacters(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
