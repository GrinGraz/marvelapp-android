package cl.gringraz.marvelcatalog.feature.characterslist.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.net.toUri
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
import cl.gringraz.marvelcatalog.feature.characterslist.presentation.safeLifecycle
import cl.gringraz.marvelcatalog.feature.characterslist.presentation.ui.adapter.MarvelCharactersListAdapter
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel

class MarvelCharactersListFragment : Fragment() {

    private val viewModel: MarvelCharactersViewModel = charactersViewModel()
    private val charactersAdapter = MarvelCharactersListAdapter(::onItemClick)
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
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                charactersAdapter.filter.filter(newText)
                return false
            }
        })
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
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://marvel.app/character_detail/${item.id}".toUri())
            .build()
        findNavController().navigate(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
