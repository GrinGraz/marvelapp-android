package cl.gringraz.marvelcatalog.feature.characterdetail.ui.adapter.details

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cl.gringraz.marvelcatalog.feature.characterdetail.databinding.MarvelCharacterDetailsItemBinding
import cl.gringraz.marvelcatalog.feature.characterdetail.domain.CharacterDetailsModel
import cl.gringraz.marvelcatalog.feature.characterdetail.ui.adapter.detail.MarvelCharacterDetailAdapter

class MarvelCharacterDetailsViewHolder(private val binding: MarvelCharacterDetailsItemBinding) :
    ViewHolder(binding.root) {

    fun bindTo(detailsModel: CharacterDetailsModel) {
        val detailAdapter = MarvelCharacterDetailAdapter()
        with(binding) {
            sectionTitle.text = detailsModel.sectionName
            detailRecycler.layoutManager = LinearLayoutManager(root.context, HORIZONTAL, false)
            detailRecycler.adapter = detailAdapter
            detailRecycler.setHasFixedSize(true)
        }

        detailAdapter.submitList(detailsModel.sectionItems)
    }
}
