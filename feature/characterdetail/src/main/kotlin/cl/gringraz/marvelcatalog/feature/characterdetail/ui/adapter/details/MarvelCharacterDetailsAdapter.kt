package cl.gringraz.marvelcatalog.feature.characterdetail.ui.adapter.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import cl.gringraz.marvelcatalog.feature.characterdetail.databinding.MarvelCharacterDetailsItemBinding
import cl.gringraz.marvelcatalog.feature.characterdetail.domain.CharacterDetailsModel
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.ItemModel

class MarvelCharacterDetailsAdapter :
    ListAdapter<CharacterDetailsModel, MarvelCharacterDetailsViewHolder>(
        MarvelCharacterDetailsDiffCallback()
    ) {

    init {
        submitList(stubDetailsList())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelCharacterDetailsViewHolder {
        val binding =
            MarvelCharacterDetailsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MarvelCharacterDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarvelCharacterDetailsViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    // Workaround to avoid set a fixed height to the RecyclerView
    // for the wrap_content works as expected
    private fun stubDetailsList() = buildList {
        val stubDetailModel = CharacterDetailsModel(sectionName = "", sectionItems = listOf(ItemModel("", "")))
        add(stubDetailModel)
        add(stubDetailModel)
        add(stubDetailModel)
        add(stubDetailModel)
    }
}
