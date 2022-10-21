package cl.gringraz.marvelcatalog.feature.characterdetail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import cl.gringraz.marvelcatalog.feature.characterdetail.R
import cl.gringraz.marvelcatalog.feature.characterdetail.databinding.FragmentCharacterDetailBinding
import cl.gringraz.marvelcatalog.feature.characterdetail.di.characterDetailViewModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import com.squareup.picasso.Picasso

class MarvelCharacterDetailFragment : DialogFragment() {

    private val viewModel = characterDetailViewModel()
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            R.style.FullScreenDialogStyle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        val name = arguments?.getString("name")
        val description = arguments?.getString("description")
        val thumbnailUrl = arguments?.getString("url")
        setupCharacterDetails(name, description, thumbnailUrl)
        setupUiStateCollection()
        id?.let { viewModel.getCharacterById(id.toString()) }
    }

    private fun setupCharacterDetails(
        name: String?,
        description: String?,
        thumbnailUrl: String?,
    ) {
        with(binding) {
            toolbar.title = name
            toolbar.setNavigationOnClickListener { dismiss() }
            characterDescription.text = description
            Picasso.get()
                .load(thumbnailUrl)
                .fit()
                .into(binding.characterImage)
            binding.characterImage.visibility = View.VISIBLE
            binding.characterDescription.visibility = View.VISIBLE
        }
    }

    private fun setupUiStateCollection() = safeLifecycle {
        viewModel.marvelCharactersUiState.collect(::renderCharacterUiState)
    }

    private fun renderCharacterUiState(state: MarvelCharactersListUiState) {
        when (state) {
            MarvelCharactersListUiState.Loading    -> renderLoading()
            is MarvelCharactersListUiState.Error   -> renderError(state.message)
            is MarvelCharactersListUiState.Success -> renderCharacter(state.character)
        }
    }

    private fun renderLoading() {
        with(binding) {
            binding.progressBar.visibility = View.VISIBLE
            binding.statusMessage.visibility = View.INVISIBLE
        }
    }

    private fun renderError(message: String) {
        with(binding) {
            progressBar.visibility = View.INVISIBLE
            statusMessage.visibility = View.VISIBLE
            statusMessage.text = message
        }
    }

    private fun renderCharacter(characters: List<MarvelCharacterModel>) {
        with(binding) {
            progressBar.visibility = View.INVISIBLE
            statusMessage.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
