package cl.gringraz.marvelcatalog.feature.characterslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cl.gringraz.marvelcatalog.feature.characterslist.di.charactersViewModel
import kotlinx.coroutines.launch

class CharactersListFragment : Fragment() {

    //private var _binding: FragmentCharacterListBinding? = null
    private lateinit var viewModel: CharactersViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    // private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = charactersViewModel()
        viewModel.getMarvelCharacters()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.result.collect {
                    when(it) {
                        is MarvelCharactersListUiState.Error   -> Toast.makeText(this@CharactersListFragment.context,
                            it.message, Toast.LENGTH_LONG).show()
                        MarvelCharactersListUiState.Loading    -> Toast.makeText(this@CharactersListFragment.context,
                            it.toString(), Toast.LENGTH_LONG).show()
                        is MarvelCharactersListUiState.Success -> Toast.makeText(this@CharactersListFragment.context,
                            it.characters.toString(), Toast.LENGTH_LONG).show()
                    }

                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    companion object {
        fun newInstance() = CharactersListFragment()
    }
}
