package cl.gringraz.marvelcatalog.feature.characterdetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import cl.gringraz.marvelcatalog.feature.characterdetail.R
import cl.gringraz.marvelcatalog.feature.characterdetail.databinding.FragmentCharacterDetailBinding
import cl.gringraz.marvelcatalog.feature.characterdetail.di.factory
import cl.gringraz.marvelcatalog.feature.characterdetail.domain.CharacterDetailsModel
import cl.gringraz.marvelcatalog.feature.characterdetail.presentation.MarvelCharacterDetailUiState
import cl.gringraz.marvelcatalog.feature.characterdetail.presentation.MarvelCharacterDetailViewModel
import cl.gringraz.marvelcatalog.feature.characterdetail.ui.adapter.details.MarvelCharacterDetailsAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MarvelCharacterDetailFragment : Fragment() {

    private val viewModel: MarvelCharacterDetailViewModel by viewModels { factory }
    private val detailAdapter = MarvelCharacterDetailsAdapter()
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupUiStateCollection()
        with(requireArguments()) {
            setupCharacterDetails(getString("description"), getString("url"))
            loadCharacterDetails(getInt("id"))
        }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically() = false
        }
        with(binding.detailsRecycler) {
            layoutManager = linearLayoutManager
            adapter = detailAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupUiStateCollection() = safeLifecycle {
        viewModel.marvelCharacterUiState.collect(::renderCharacterUiState)
    }

    private fun setupCharacterDetails(
        description: String?,
        thumbnailUrl: String?,
    ) {
        with(binding) {
            characterDescription.text = description
            Picasso.get().load(thumbnailUrl).error(R.drawable.ic_broken_image_24).fit()
                .into(characterImage)
            characterImage.visibility = View.VISIBLE
            characterDescription.visibility = View.VISIBLE
        }
    }

    private fun loadCharacterDetails(id: Int) {
        viewModel.getCharacterById(id.toString())
    }

    private fun renderCharacterUiState(state: MarvelCharacterDetailUiState) {
        when (state) {
            MarvelCharacterDetailUiState.Loading -> renderLoading()
            is MarvelCharacterDetailUiState.Error -> renderError(state.message)
            is MarvelCharacterDetailUiState.Success -> renderCharacterDetails(state.characterDetails)
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
            progressBar.visibility = View.INVISIBLE
            statusMessage.visibility = View.VISIBLE
            statusMessage.text = message
            detailsLoader.isVisible = false
        }
    }

    private fun renderCharacterDetails(characterDetails: List<CharacterDetailsModel>) {
        detailAdapter.submitList(characterDetails)
        lifecycleScope.launch {
            delay(500)
            with(binding) {
                progressBar.visibility = View.INVISIBLE
                statusMessage.visibility = View.INVISIBLE
                detailsLoader.visibility = View.GONE
                detailsRecycler.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
