package cl.gringraz.marvelcatalog.feature.characterdetail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import cl.gringraz.marvelcatalog.feature.characterdetail.R
import cl.gringraz.marvelcatalog.feature.characterdetail.databinding.FragmentCharacterDetailBinding
import com.squareup.picasso.Picasso

class MarvelCharacterDetailFragment : DialogFragment() {

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
        val name = arguments?.getString("name")
        val description = arguments?.getString("description")
        val thumbnailUrl = arguments?.getString("url")

        setupCharacterDetails(name, description, thumbnailUrl)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
