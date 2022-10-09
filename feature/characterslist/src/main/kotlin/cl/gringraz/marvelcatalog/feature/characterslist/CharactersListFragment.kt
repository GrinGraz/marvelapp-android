package cl.gringraz.marvelcatalog.feature.characterslist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CharactersListFragment : Fragment() {

    //private var _binding: FragmentCharacterListBinding? = null
    private lateinit var viewModel: CharactersViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    // private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CharactersViewModel::class.java)
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