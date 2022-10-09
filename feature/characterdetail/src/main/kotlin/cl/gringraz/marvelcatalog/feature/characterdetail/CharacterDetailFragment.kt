package cl.gringraz.marvelcatalog.feature.characterdetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cl.gringraz.feature.marvelcatalog.characterdetail.R

class CharacterDetailFragment : Fragment() {

    private lateinit var viewModel: CharacterDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CharacterDetailViewModel::class.java)
    }

    companion object {
        fun newInstance() = CharacterDetailFragment()
    }
}